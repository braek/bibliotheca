package be.koder.library.domain.book;

import be.koder.library.domain.book.event.BookAdded;
import be.koder.library.domain.book.event.BookModified;
import be.koder.library.domain.event.EventPublisher;
import be.koder.library.vocabulary.book.Author;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Isbn;
import be.koder.library.vocabulary.book.Title;

public final class Book {

    private final BookId id;
    private final Isbn isbn;
    private Title title;
    private Author author;

    private Book(final BookId id, final Isbn isbn, final Title title, final Author author) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public static Book createNew(final Isbn isbn, final Title title, final Author author, final EventPublisher eventPublisher) {
        final var book = new Book(BookId.createNew(), isbn, title, author);
        eventPublisher.publish(new BookAdded(book.id));
        return book;
    }

    public void modify(final Title title, final Author author, final EventPublisher eventPublisher) {
        this.title = title;
        this.author = author;
        eventPublisher.publish(new BookModified(id));
    }

    public BookSnapshot takeSnapshot() {
        return new BookSnapshot(id, isbn, title, author);
    }

    public static Book fromSnapshot(final BookSnapshot book) {
        return new Book(book.id(), book.isbn(), book.title(), book.author());
    }
}