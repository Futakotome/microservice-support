package io.futakotome.cache.domain;

import com.gzyouai.hummingbird.MHDatasource.annotation.DynamicDatasourceSwitch;
import io.futakotome.cache.domain.entity.Student;
import io.futakotome.cache.domain.repository.StudentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "student")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class StudentService {
    private final @NonNull StudentRepository studentRepository;

    @DynamicDatasourceSwitch(dataSourceKey = "redis-1")
    public Student saveOne(Student student) {
        return studentRepository.save(student);
    }

    //009c3544-5170-4dc4-bb94-aa18b6f0ca77
    @Cacheable(keyGenerator = "keyGenerator")
    @DynamicDatasourceSwitch(dataSourceKey = "redis-1")
    public Student findById(String id) {
        return studentRepository.findById(id).get();
    }
}
