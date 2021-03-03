package io.futakotome.cache.domain.repository;

import io.futakotome.cache.domain.entity.Student;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends KeyValueRepository<Student, String> {
}
