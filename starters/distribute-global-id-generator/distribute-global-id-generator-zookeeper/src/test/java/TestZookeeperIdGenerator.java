import io.futakotome.globalId.ZookeeperGlobalIdGenerator;
import io.futakotome.globalId.config.ZookeeperGlobalIdGeneratorConfiguration;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestZookeeperIdGenerator {
    @Test
    public void testZkId() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ZookeeperGlobalIdGeneratorConfiguration.class);
        context.refresh();

        ZookeeperGlobalIdGenerator idGenerator = context.getBean(ZookeeperGlobalIdGenerator.class);
        System.out.println(idGenerator.nextSequenceId("test"));
    }
}
