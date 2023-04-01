package be.koder.library.domain.book.isbn;

import be.koder.library.vocabulary.book.Isbn;

// Domain service to check existence of ISBNs
public interface IsbnService {
    boolean exists(Isbn isbn);
}