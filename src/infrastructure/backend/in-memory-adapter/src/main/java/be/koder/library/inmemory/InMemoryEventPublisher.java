package be.koder.library.inmemory;

import be.koder.library.domain.event.Event;
import be.koder.library.domain.event.EventPublisher;

import java.util.ArrayList;
import java.util.List;

public final class InMemoryEventPublisher implements EventPublisher {

    private final List<Event> events = new ArrayList<>();

    @Override
    public void publish(Event event) {
        events.add(event);
    }
}