import demo.multiple.DemoApplication;
import demo.multiple.service.redis.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class RedisMultipleConnectionsTest {
    @Autowired
    private StudentService studentService;

    @Test
    public void test1() {
        studentService.localSave();
    }

    @Test
    public void test2() {
        studentService.neiwangSave();
    }
}
