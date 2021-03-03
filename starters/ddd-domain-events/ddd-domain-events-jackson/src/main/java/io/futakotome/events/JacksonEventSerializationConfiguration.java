package io.futakotome.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.futakotome.events.config.EventSerializationConfigurationExtension;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class JacksonEventSerializationConfiguration implements EventSerializationConfigurationExtension {
    private final ObjectProvider<ObjectMapper> mapper;

    @Bean
    public JacksonEventSerializer jacksonEventSerializer() {
        return new JacksonEventSerializer(() -> mapper.getIfAvailable(JacksonEventSerializationConfiguration::defaultObjectMapper));
    }

    private static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        return mapper;
    }
}
