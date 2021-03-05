package io.futakotome.service;

import io.futakotome.api.AService;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

@DubboService(version = "1.0.0")
public class DefaultAService implements AService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final Random costTimeRandom = new Random();

    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String helloWorld(String name) {
        await();
        return String.format("[%s] : Hello , %s ", serviceName, name);
    }

    private void await() {
        try {
            long timeInMillisToWait = costTimeRandom.nextInt(500);
            Thread.sleep(timeInMillisToWait);
            LOGGER.info("execution time : " + timeInMillisToWait + " ms ");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
