import io.futakotome.globalId.HibernateGlobalIdGenerator;
import io.futakotome.globalId.config.HibernateGlobalIdGeneratorConfiguration;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestHibernateConfigurationTest {
    @Test
    public void testConfiguration() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(HibernateGlobalIdGeneratorConfiguration.class);
        context.refresh();

        System.out.println(context.getBean(HibernateGlobalIdGenerator.class).nextSequenceId("test"));

    }
}
