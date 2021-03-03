import demo.multiple.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class ConnectionTest {
    //正常启动即可,多数据源装配没问题的话不会报错

    @Test
    public void testConn() {
    }
}
