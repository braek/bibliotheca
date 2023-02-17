package be.koder.library.domain.book;

import be.koder.library.vocabulary.book.Author;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Isbn;
import be.koder.library.vocabulary.book.Title;

import java.net.URL;

// Immutable version of the aggregate root
public record BookSnapshot(BookId id, Isbn isbn, Title title, Author author, URL hardcover) {
}