package io.futakotome.cache.domain;

import io.futakotome.cache.domain.entity.Student;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class StudentController {
    private final @NonNull StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(studentService.findById(id));
    }
}
