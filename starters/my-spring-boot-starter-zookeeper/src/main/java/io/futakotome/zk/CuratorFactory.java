package io.futakotome.zk;

import io.futakotome.zk.config.ZookeeperProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.drivers.TracerDriver;
import org.apache.curator.ensemble.EnsembleProvider;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.BootstrapContext;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;

import java.util.function.Supplier;
import java.util.stream.Stream;

@Slf4j
public abstract class CuratorFactory {

    public static CuratorFramework curatorFramework(ZookeeperProperties properties,
                                                    RetryPolicy retryPolicy,
                                                    Supplier<Stream<CuratorFrameworkCustomizer>> curatorFrameworkCustomizerProvider,
                                                    Supplier<EnsembleProvider> optionalEnsembleProvider,
                                                    Supplier<TracerDriver> optionalTracerDriverProvider) throws InterruptedException {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
        EnsembleProvider ensembleProvider = optionalEnsembleProvider.get();
        if (ensembleProvider != null) {
            builder.ensembleProvider(ensembleProvider);
        } else {
            builder.connectString(properties.getZkAddress());
        }
        builder.sessionTimeoutMs((int) properties.getSessionTimeout().toMillis())
                .connectionTimeoutMs((int) properties.getConnectionTimeout().toMillis())
                .retryPolicy(retryPolicy);
        Stream<CuratorFrameworkCustomizer> customizerStream = curatorFrameworkCustomizerProvider.get();
        if (customizerStream != null) {
            customizerStream.forEach(curatorFrameworkCustomizer -> curatorFrameworkCustomizer.customize(builder));
        }
        CuratorFramework curatorFramework = builder.build();
        TracerDriver tracerDriver = optionalTracerDriverProvider.get();
        if (tracerDriver != null && curatorFramework.getZookeeperClient() != null) {
            curatorFramework.getZookeeperClient().setTracerDriver(tracerDriver);
        }
        curatorFramework.start();

        if (log.isTraceEnabled()) {
            log.trace("Blocking until connected to zookeeper for " + properties.getBlockUntilConnectedWait()
                    + properties.getBlockUntilConnectedUnit());
        }
        curatorFramework.blockUntilConnected(properties.getBlockUntilConnectedWait(),
                properties.getBlockUntilConnectedUnit());
        if (log.isTraceEnabled()) {
            log.trace("Connected to Zookeeper");
        }
        return curatorFramework;
    }

    public static RetryPolicy retryPolicy(ZookeeperProperties properties) {
        return new ExponentialBackoffRetry(properties.getBaseSleepTimeMs(),
                properties.getMaxRetries(),
                properties.getMaxSleepMs()
        );
    }

    public static void registerCurator(BootstrapRegistry registry, UriComponents uriComponents, boolean optional) {
        registry.registerIfAbsent(ZookeeperProperties.class,
                context -> loadProperties(context.get(Binder.class), uriComponents));

    }

    static ZookeeperProperties loadProperties(Binder binder, UriComponents uriComponents) {
        ZookeeperProperties zookeeperProperties = binder.bind(ZookeeperProperties.PREFIX, Bindable.of(ZookeeperProperties.class))
                .orElse(new ZookeeperProperties());

        if (uriComponents != null && StringUtils.hasText(uriComponents.getHost())) {
            if (uriComponents.getPort() < 0) {
                throw new IllegalArgumentException("Zookeeper port must >=0 , now : " + uriComponents.getPort());
            }
            zookeeperProperties.setZkAddress(uriComponents.getHost() + ":" + uriComponents.getPort());
        }

        return zookeeperProperties;
    }

    private static CuratorFramework curatorFramework(BootstrapContext context, ZookeeperProperties properties, boolean optional) {
        Supplier<Stream<CuratorFrameworkCustomizer>> customizers;
        try {
            CuratorFrameworkCustomizer customizer = context.get(CuratorFrameworkCustomizer.class);
            customizers = () -> Stream.of(customizer);
        } catch (Exception e) {
            customizers = () -> null;
        }
        try {
            return CuratorFactory.curatorFramework(properties,
                    context.get(RetryPolicy.class),
                    customizers,
                    supplier(context, EnsembleProvider.class),
                    supplier(context, TracerDriver.class));
        } catch (Exception e) {
            if (optional) {
                log.error("Unable connect to zookeeper");
                throw new ZookeeperConnectException("Unable connect to zookeeper", e);
            }
            if (log.isDebugEnabled()) {
                log.debug("Unable connect to zookeeper.", e);
            }
        }
        return null;
    }

    private static <T> Supplier<T> supplier(BootstrapContext context, Class<T> type) {
        try {
            T instance = context.get(type);
            return () -> instance;
        } catch (Exception e) {
            return () -> null;
        }
    }

    private static class ZookeeperConnectException extends RuntimeException {
        protected ZookeeperConnectException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }
}
