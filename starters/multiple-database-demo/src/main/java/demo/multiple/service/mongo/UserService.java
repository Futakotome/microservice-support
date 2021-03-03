package demo.multiple.service.mongo;

import io.futakotome.MHDatasource.annotation.DynamicDatasourceSwitch;
import demo.multiple.repo.mongo.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService {
    private final @NonNull UserRepository userRepository;

    @DynamicDatasourceSwitch(dataSourceKey = "neiwang-test")
    public void findUserNeiwang() {
        System.out.println(userRepository.findAll());
    }

    @DynamicDatasourceSwitch(dataSourceKey = "local-test")
    public void findUserLocal() {
        System.out.println(userRepository.findAll());
    }
}
