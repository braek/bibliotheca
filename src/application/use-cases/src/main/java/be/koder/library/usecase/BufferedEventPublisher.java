package be.koder.library.usecase;

import be.koder.library.domain.event.Event;
import be.koder.library.domain.event.EventPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class BufferedEventPublisher implements EventPublisher {

    private final EventPublisher eventPublisher;
    private final List<Event> buffer = new ArrayList<>();

    public BufferedEventPublisher(final EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void doBuffered(final Consumer<EventPublisher> lambda) {
        lambda.accept(this);
        flush();
    }

    public void flush() {
        buffer.forEach(eventPublisher::publish);
        buffer.clear();
    }

    @Override
    public void publish(final Event event) {
        buffer.add(event);
    }
}