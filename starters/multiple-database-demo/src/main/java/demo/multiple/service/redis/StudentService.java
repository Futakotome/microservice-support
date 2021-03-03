package demo.multiple.service.redis;

import io.futakotome.MHDatasource.annotation.DynamicDatasourceSwitch;
import demo.multiple.entity.redis.Student;
import demo.multiple.repo.redis.StudentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class StudentService {

    private final @NonNull StudentRepository studentRepository;

    @DynamicDatasourceSwitch(dataSourceKey = "test1")
    public void neiwangSave() {
        Student student = Student.builder()
                .name("zzz")
                .desc("24岁是学生").build();
        System.out.println(studentRepository.save(student));
    }

    @DynamicDatasourceSwitch(dataSourceKey = "test2")
    public void localSave() {
        Student student = Student.builder()
                .name("zzz")
                .desc("24岁是学生").build();
        System.out.println(studentRepository.save(student));
    }
}
