package demo.multiple.entity.mysql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "server")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Server {
    @Id
    private Integer id;
    private String name;
    private String host;
}
