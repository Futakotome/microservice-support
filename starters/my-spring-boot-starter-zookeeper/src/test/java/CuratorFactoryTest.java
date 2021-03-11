import io.futakotome.zk.CuratorFactory;
import io.futakotome.zk.config.ZookeeperProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;


public class CuratorFactoryTest {
    @Test
    public void testLoadProperties() {
        ZookeeperProperties properties = CuratorFactory.loadProperties(Binder.get(new MockEnvironment()), UriComponentsBuilder.fromUriString("zookeep://myzk:12345").build());
        assertThat(properties.getZkAddress()).isEqualTo("myzk:12345");
    }
}
