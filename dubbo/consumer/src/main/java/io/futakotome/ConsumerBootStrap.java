package io.futakotome;

import io.futakotome.api.AService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsumerBootStrap {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @DubboReference(
            version = "1.0.0",
            url = "dubbo://127.0.0.1:123456",
            timeout = 100,
            methods = {
                    @Method(name = "helloWorld", timeout = 300)
            })
    private AService aService;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerBootStrap.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> LOGGER.info(aService.helloWorld("helloworld"));
    }
}
