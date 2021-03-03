import io.futakotome.events.EventPublication;
import io.futakotome.events.EventPublicationRegistry;
import io.futakotome.events.PublicationTargetIdentifier;
import io.futakotome.events.config.EnablePersistentDomainEvents;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class PersistentDomainEventIntegrationTest {

    @Test
    public void exposesEventPublicationForFailedListener() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ApplicationConfiguration.class, InfrastructureConfiguration.class);
        context.refresh();

        Method method = ReflectionUtils.findMethod(SecondTxEventListener.class, "on", DomainEvent.class);

        try {

            context.getBean(Client.class).method();

        } catch (Throwable e) {

            System.out.println(e);

        } finally {

            Iterable<EventPublication> eventsToBePublished = context.getBean(EventPublicationRegistry.class)
                    .findIncompletePublications();

            assertThat(eventsToBePublished).hasSize(1);
            assertThat(eventsToBePublished).allSatisfy(it -> {
                assertThat(it.getTargetIdentifier()).isEqualTo(PublicationTargetIdentifier.forMethod(method));
            });

            context.close();
        }
    }

    @Configuration
    @EnablePersistentDomainEvents
    static class ApplicationConfiguration {

        @Bean
        FirstTxEventListener first() {
            return new FirstTxEventListener();
        }

        @Bean
        SecondTxEventListener second() {
            return new SecondTxEventListener();
        }

        @Bean
        ThirdTxEventListener third() {
            return new ThirdTxEventListener();
        }

        @Bean
        Client client(ApplicationEventPublisher publisher) {
            return new Client(publisher);
        }
    }

    @RequiredArgsConstructor
    static class Client {

        private final ApplicationEventPublisher publisher;

        @Transactional
        public void method() {
            publisher.publishEvent(new DomainEvent());
        }
    }

    static class DomainEvent {
    }

    static class NonTxEventListener {

        boolean invoked = false;

        @EventListener
        void on(DomainEvent event) {
            invoked = true;
        }
    }

    static class FirstTxEventListener {

        boolean invoked = false;

        @TransactionalEventListener
        public void on(DomainEvent event) {
            invoked = true;
        }
    }

    static class SecondTxEventListener {

        @TransactionalEventListener
        public void on(DomainEvent event) {
            throw new IllegalStateException();
        }
    }

    static class ThirdTxEventListener {

        boolean invoked = false;

        @TransactionalEventListener
        public void on(DomainEvent event) {
            invoked = true;
        }
    }
}
