import io.futakotome.cache.CacheServiceApplication;
import io.futakotome.cache.domain.StudentService;
import io.futakotome.cache.domain.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = CacheServiceApplication.class)
@RunWith(SpringRunner.class)
public class TestSimpleCache {
    @Autowired
    private StudentService studentService;

    @Test
    public void testSave() {
        Student student = new Student();
        student.setName("zzz");
        System.out.println(studentService.saveOne(student));
    }

    @Test
    public void testFind() {
        System.out.println(studentService.findById("ed05e7b4-2a2b-496f-bb88-cc0f8467bd9f"));
    }
}
