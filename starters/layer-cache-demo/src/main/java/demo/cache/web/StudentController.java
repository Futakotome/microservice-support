package demo.cache.web;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CacheConfig(cacheNames = "student")
@RestController
public class StudentController {

    @Cacheable
    @GetMapping("/student")
    public ResponseEntity<Student> get() {
        Student student = new Student();
        student.setId("1");
        student.setName("zzz");
        return ResponseEntity.ok(student);
    }

    @PostMapping("/student")
    public ResponseEntity<Student> post(String id, String name) {
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        return ResponseEntity.ok(student);
    }
}
