import demo.multiple.DemoApplication;
import demo.multiple.service.mysql.ServerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class MultipleDatasourceTest {

    @Autowired
    private ServerService serverService;

    @Test
    public void test1() {
        System.out.println(serverService.daqinNeiwangServers());
    }

    @Test
    public void test2() {
        System.out.println(serverService.daqinWaiwangServers());
    }
}
