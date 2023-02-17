package be.koder.library.usecase.book;

import be.koder.library.api.AddBook;
import be.koder.library.api.presenter.AddBookPresenter;
import be.koder.library.domain.book.Book;
import be.koder.library.domain.book.BookRepository;
import be.koder.library.domain.book.IsbnService;
import be.koder.library.domain.event.EventPublisher;
import be.koder.library.usecase.BufferedEventPublisher;
import be.koder.library.usecase.UseCase;
import be.koder.library.usecase.book.command.AddBookCommand;
import be.koder.library.vocabulary.book.Author;
import be.koder.library.vocabulary.book.Isbn;
import be.koder.library.vocabulary.book.Title;

public final class AddBookUseCase implements UseCase<AddBookCommand, AddBookPresenter>, AddBook {

    private final BookRepository bookRepository;
    private final IsbnService isbnService;
    private final EventPublisher eventPublisher;

    public AddBookUseCase(BookRepository bookRepository, IsbnService isbnService, EventPublisher eventPublisher) {
        this.bookRepository = bookRepository;
        this.isbnService = isbnService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void addBook(Isbn isbn, Title title, Author author, AddBookPresenter presenter) {
        execute(new AddBookCommand(isbn, title, author), presenter);
    }

    @Override
    public void execute(AddBookCommand command, AddBookPresenter presenter) {
        new BufferedEventPublisher(eventPublisher).doBuffered(it -> executeBuffered(command, presenter, it));
    }

    private void executeBuffered(AddBookCommand command, AddBookPresenter presenter, EventPublisher eventPublisher) {
        if (isbnService.exists(command.isbn())) {
            presenter.isbnAlreadyExists();
            return;
        }
        final var book = Book.createNew(command.isbn(), command.title(), command.author(), eventPublisher);
        bookRepository.save(book);
        final var bookId = book.takeSnapshot().id();
        presenter.added(bookId);
    }
}