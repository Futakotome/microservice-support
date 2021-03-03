package demo.multiple.repo.redis;

import demo.multiple.entity.redis.Student;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends KeyValueRepository<Student, String> {
}
