package io.futakotome;

import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

@DubboService(version = "1.0.0")
public class DefaultService implements AService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Random costTimeRandom = new Random();

    @Value("${spring.application.name}")
    private String serviceName;

    @Override
    public String say(String helloWorld) {
        await();
        return String.format("[%s] : Hello, %s", serviceName, helloWorld);
    }

    private void await() {
        try {
            long timeInMillisToWait = costTimeRandom.nextInt(500);
            Thread.sleep(timeInMillisToWait);
            logger.info("execution time : " + timeInMillisToWait + " ms.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
