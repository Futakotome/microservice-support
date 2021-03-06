package io.futakotome;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
public class ConsumerBootStrap {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @DubboReference(
            version = "1.0.0",
            url = "dubbo://127.0.0.1:12345",
            timeout = 100,
            methods = {
                    @Method(name = "say", timeout = 3000)
            }
    )
    private AService aService;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerBootStrap.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> LOGGER.info(aService.say("helloWorld!!"));
    }
}
