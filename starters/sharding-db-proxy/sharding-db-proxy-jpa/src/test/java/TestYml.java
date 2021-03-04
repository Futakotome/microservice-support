import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestYml {

    @Test
    public void ymlIsNull() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(InfrastructureConfiguration.class);
        context.refresh();

    }
}
