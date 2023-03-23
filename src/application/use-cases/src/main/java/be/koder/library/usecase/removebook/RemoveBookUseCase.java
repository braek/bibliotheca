package be.koder.library.usecase.removebook;

import be.koder.library.api.RemoveBook;
import be.koder.library.api.presenter.RemoveBookPresenter;
import be.koder.library.domain.book.BookRepository;
import be.koder.library.domain.book.event.BookRemoved;
import be.koder.library.domain.event.EventPublisher;
import be.koder.library.usecase.BufferedEventPublisher;
import be.koder.library.usecase.UseCase;
import be.koder.library.vocabulary.book.BookId;

public final class RemoveBookUseCase implements UseCase<RemoveBookCommand, RemoveBookPresenter>, RemoveBook {

    private final BookRepository bookRepository;
    private final EventPublisher eventPublisher;

    public RemoveBookUseCase(BookRepository bookRepository, EventPublisher eventPublisher) {
        this.bookRepository = bookRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void removeBook(BookId bookId, RemoveBookPresenter presenter) {
        execute(new RemoveBookCommand(bookId), presenter);
    }

    @Override
    public void execute(RemoveBookCommand command, RemoveBookPresenter presenter) {
        new BufferedEventPublisher(eventPublisher).doBuffered(it -> executeBuffered(command, presenter, it));
    }

    private void executeBuffered(RemoveBookCommand command, RemoveBookPresenter presenter, EventPublisher eventPublisher) {
        bookRepository.get(command.bookId()).ifPresentOrElse(book -> {
            bookRepository.delete(command.bookId());
            eventPublisher.publish(new BookRemoved(command.bookId()));
            presenter.removed(command.bookId());
        }, presenter::bookNotFound);
    }
}