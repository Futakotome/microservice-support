package demo.multiple.service.mysql;

import io.futakotome.MHDatasource.annotation.DynamicDatasourceSwitch;
import demo.multiple.entity.mysql.Server;
import demo.multiple.repo.mysql.ServerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ServerService {

    private final @NonNull ServerRepository serverRepository;

    @DynamicDatasourceSwitch(dataSourceKey = "daqin-neiwang")
    public List<Server> daqinNeiwangServers() {
        return serverRepository.findAll();
    }

    @DynamicDatasourceSwitch(dataSourceKey = "daqin-waiwang")
    public List<Server> daqinWaiwangServers() {
        return serverRepository.findAll();
    }

}
