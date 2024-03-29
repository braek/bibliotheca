package be.koder.library.api.presenter;

import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Isbn;

public interface AddBookPresenter {

    void added(BookId bookId);

    void isbnAlreadyExists(Isbn isbn);
}