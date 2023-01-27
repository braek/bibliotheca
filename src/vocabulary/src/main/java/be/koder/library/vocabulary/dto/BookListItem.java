package be.koder.library.vocabulary.dto;

import be.koder.library.vocabulary.book.Author;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Isbn;
import be.koder.library.vocabulary.book.Title;

public record BookListItem(BookId id, Isbn isbn, Title title, Author author) {}