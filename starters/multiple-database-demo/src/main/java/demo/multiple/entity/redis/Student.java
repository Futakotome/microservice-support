package demo.multiple.entity.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Data
@RedisHash("student")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    private String id;

    private String name;

    private String desc;
}
