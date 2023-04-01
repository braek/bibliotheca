package be.koder.library.usecase.removehardcover;

import be.koder.library.api.RemoveHardcover;
import be.koder.library.api.presenter.RemoveHardcoverPresenter;
import be.koder.library.domain.book.BookRepository;
import be.koder.library.domain.event.EventPublisher;
import be.koder.library.usecase.BufferedEventPublisher;
import be.koder.library.usecase.UseCase;
import be.koder.library.vocabulary.book.BookId;

public final class RemoveHardcoverUseCase implements UseCase<RemoveHardcoverCommand, RemoveHardcoverPresenter>, RemoveHardcover {

    private final BookRepository bookRepository;
    private final EventPublisher eventPublisher;

    public RemoveHardcoverUseCase(BookRepository bookRepository, EventPublisher eventPublisher) {
        this.bookRepository = bookRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void removeHardcover(BookId bookId, RemoveHardcoverPresenter presenter) {
        execute(new RemoveHardcoverCommand(bookId), presenter);
    }

    @Override
    public void execute(RemoveHardcoverCommand command, RemoveHardcoverPresenter presenter) {
        new BufferedEventPublisher(eventPublisher).doBuffered(it -> executeBuffered(command, presenter, it));
    }

    private void executeBuffered(RemoveHardcoverCommand command, RemoveHardcoverPresenter presenter, EventPublisher eventPublisher) {
        bookRepository.get(command.bookId()).ifPresentOrElse(book -> {
            book.removeHardcover(new RemoveHardcoverEventPublisherDecorator(presenter, eventPublisher));
            bookRepository.save(book);
        }, presenter::bookNotFound);
    }
}