package be.koder.library.test;

import be.koder.library.domain.event.Event;
import be.koder.library.domain.event.EventPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class MockEventPublisher implements EventPublisher {

    private final List<Event> events = new ArrayList<>();

    @Override
    public void publish(Event event) {
        events.add(event);
    }

    public Optional<Event> getLastEvent() {
        if (events.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(events.get(events.size() - 1));
    }
}