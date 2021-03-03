package demo.multiple.repo.mysql;

import demo.multiple.entity.mysql.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends JpaRepository<Server, Integer> {
}
