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

    @DubboReference(version = "1.0.0")
    private AService aService;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerBootStrap.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> LOGGER.info(aService.say("helloWorld!!"));
    }
}
