package be.koder.library.domain.event;

public interface EventPublisher {
    void publish(Event event);
}