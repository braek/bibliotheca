package be.koder.library.usecase.addbook;

import be.koder.library.api.presenter.AddBookPresenter;
import be.koder.library.domain.book.event.BookAdded;
import be.koder.library.domain.book.event.CreateBookEventPublisher;
import be.koder.library.domain.event.EventPublisher;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Isbn;

public final class CreateBookEventPublisherDecorator implements CreateBookEventPublisher {

    private final AddBookPresenter presenter;
    private final EventPublisher eventPublisher;

    public CreateBookEventPublisherDecorator(final AddBookPresenter presenter, final EventPublisher eventPublisher) {
        this.presenter = presenter;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void created(BookId bookId) {
        eventPublisher.publish(new BookAdded(bookId));
        presenter.added(bookId);
    }

    @Override
    public void isbnAlreadyExists(Isbn isbn) {
        presenter.isbnAlreadyExists(isbn);
    }
}