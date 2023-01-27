package be.koder.library.api;

import be.koder.library.vocabulary.dto.BookListItem;

import java.util.List;

public interface ListBooks {
    List<BookListItem> listBooks();
}