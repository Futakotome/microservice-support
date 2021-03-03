package io.futakotome.cache.domain.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Data
@RedisHash(value = "studnet")
public class Student {
    @Id
    private String id;
    private String name;

}
