package be.koder.library.domain.book.event;

import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Isbn;

public interface CreateBookEventPublisher {

    void created(BookId bookId);

    void isbnAlreadyExists(Isbn isbn);
}