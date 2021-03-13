//import io.futakotome.zk.ZookeeperClientTemplate;
//import io.futakotome.zk.config.EnableZookeeperClient;
//import io.futakotome.zk.config.ZookeeperProperties;
//import org.apache.curator.framework.CuratorFramework;
//import org.junit.jupiter.api.Test;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.annotation.Configuration;
//
//public class ZookeeperClientTest {
//    @Test
//    public void testAutoConfiguration() throws Exception {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.register(InfrastructureConfiguration.class);
//        context.refresh();
//
//        System.out.println(context.getBean(ZookeeperProperties.class).getZkAddress());
//        System.out.println(context.getBean(CuratorFramework.class));
//        System.out.println(context.getBean(ZookeeperClientTemplate.class));
//
//        ZookeeperClientTemplate template = context.getBean(ZookeeperClientTemplate.class);
//        template.createPath("/test");
//        template.deletePath("/test");
//    }
//
//    @Configuration(proxyBeanMethods = false)
//    @EnableZookeeperClient
//    static class InfrastructureConfiguration {
//
//    }
//}
