package be.koder.library.usecase.removehardcover;

import be.koder.library.api.presenter.RemoveHardcoverPresenter;
import be.koder.library.domain.book.event.HardcoverRemoved;
import be.koder.library.domain.book.event.RemoveHardcoverEventPublisher;
import be.koder.library.domain.event.EventPublisher;
import be.koder.library.vocabulary.book.BookId;

import java.net.URL;

public final class RemoveHardcoverEventPublisherDecorator implements RemoveHardcoverEventPublisher {

    private final RemoveHardcoverPresenter presenter;
    private final EventPublisher eventPublisher;

    public RemoveHardcoverEventPublisherDecorator(RemoveHardcoverPresenter presenter, EventPublisher eventPublisher) {
        this.presenter = presenter;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void removed(BookId bookId, URL hardcover) {
        presenter.removed(bookId, hardcover);
        eventPublisher.publish(new HardcoverRemoved(bookId, hardcover));
    }

    @Override
    public void bookHasNoHardcover() {
        presenter.bookHasNoHardcover();
    }
}