import io.futakotome.globalId.config.GlobalIdGeneratorConfiguration;
import io.futakotome.globalId.config.IdGeneratorConfiguration;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestIdGenerator {
    @Test
    public void testCore() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(GlobalIdGeneratorConfiguration.class);
        context.refresh();

        System.out.println(context.getBean(IdGeneratorConfiguration.class)
                .idGenerator()
                .nextSequenceId());

    }
}
