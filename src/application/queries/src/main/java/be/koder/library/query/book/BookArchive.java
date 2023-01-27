package be.koder.library.query.book;

import be.koder.library.vocabulary.dto.BookListItem;

import java.util.List;

public interface BookArchive {
    List<BookListItem> listBooks();
}