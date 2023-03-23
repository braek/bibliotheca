package be.koder.library.usecase.modifybook;

import be.koder.library.api.ModifyBook;
import be.koder.library.api.presenter.ModifyBookPresenter;
import be.koder.library.domain.book.BookRepository;
import be.koder.library.domain.event.EventPublisher;
import be.koder.library.usecase.BufferedEventPublisher;
import be.koder.library.usecase.UseCase;
import be.koder.library.vocabulary.book.Author;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Title;

public final class ModifyBookUseCase implements UseCase<ModifyBookCommand, ModifyBookPresenter>, ModifyBook {

    private final BookRepository bookRepository;
    private final EventPublisher eventPublisher;

    public ModifyBookUseCase(BookRepository bookRepository, EventPublisher eventPublisher) {
        this.bookRepository = bookRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void modifyBook(BookId bookId, Title title, Author author, ModifyBookPresenter presenter) {
        execute(new ModifyBookCommand(bookId, title, author), presenter);
    }

    @Override
    public void execute(ModifyBookCommand command, ModifyBookPresenter presenter) {
        new BufferedEventPublisher(eventPublisher).doBuffered(it -> executeBuffered(command, presenter, it));
    }

    private void executeBuffered(ModifyBookCommand command, ModifyBookPresenter presenter, EventPublisher eventPublisher) {
        bookRepository.get(command.bookId()).ifPresentOrElse(book -> {
            book.modify(command.title(), command.author(), eventPublisher);
            bookRepository.save(book);
            presenter.modified(command.bookId());
        }, presenter::bookNotFound);
    }
}