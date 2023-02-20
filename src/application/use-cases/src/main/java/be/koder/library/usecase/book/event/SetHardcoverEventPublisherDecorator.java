package be.koder.library.usecase.book.event;

import be.koder.library.api.presenter.SetHardcoverPresenter;
import be.koder.library.domain.book.event.HardcoverSet;
import be.koder.library.domain.book.event.SetHardcoverEventPublisher;
import be.koder.library.domain.event.EventPublisher;
import be.koder.library.vocabulary.book.BookId;

import java.net.URL;

public final class SetHardcoverEventPublisherDecorator implements SetHardcoverEventPublisher {

    private final SetHardcoverPresenter presenter;
    private final EventPublisher eventPublisher;

    public SetHardcoverEventPublisherDecorator(SetHardcoverPresenter presenter, EventPublisher eventPublisher) {
        this.presenter = presenter;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void set(BookId bookId, URL hardcover) {
        eventPublisher.publish(new HardcoverSet(bookId, hardcover));
        presenter.set(hardcover);
    }

    @Override
    public void storageFailed(String reason) {
        presenter.storageFailed(reason);
    }

    @Override
    public void fileExtensionNotAllowed() {
        presenter.fileExtensionNotAllowed();
    }

    @Override
    public void fileEmpty() {
        presenter.fileEmpty();
    }

    @Override
    public void fileTooLarge() {
        presenter.fileTooLarge();
    }
}
