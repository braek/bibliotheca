package be.koder.library.domain.book;

import be.koder.library.domain.book.event.CreateBookEventPublisher;
import be.koder.library.domain.book.isbn.IsbnService;
import be.koder.library.vocabulary.book.Author;
import be.koder.library.vocabulary.book.Isbn;
import be.koder.library.vocabulary.book.Title;

public final class BookCreator {

    private final IsbnService isbnService;
    private final BookRepository bookRepository;

    public BookCreator(IsbnService isbnService, BookRepository bookRepository) {
        this.isbnService = isbnService;
        this.bookRepository = bookRepository;
    }

    public void create(final Isbn isbn, final Title title, final Author author, final CreateBookEventPublisher eventPublisher) {
        if (isbnService.exists(isbn)) {
            eventPublisher.isbnAlreadyExists(isbn);
            return;
        }
        final var book = Book.createNew(isbn, title, author);
        bookRepository.save(book);
        eventPublisher.created(book.takeSnapshot().id());
    }
}