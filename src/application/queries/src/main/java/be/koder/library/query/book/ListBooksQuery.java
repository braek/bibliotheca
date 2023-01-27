package be.koder.library.query.book;

import be.koder.library.api.ListBooks;
import be.koder.library.vocabulary.dto.BookListItem;

import java.util.List;

public final class ListBooksQuery implements ListBooks {

    private final BookArchive bookArchive;

    public ListBooksQuery(BookArchive bookArchive) {
        this.bookArchive = bookArchive;
    }

    @Override
    public List<BookListItem> listBooks() {
        return bookArchive.listBooks();
    }
}