package be.koder.library.api.presenter;

import be.koder.library.vocabulary.book.BookId;

public interface AddBookPresenter {

    void added(BookId bookId);

    void isbnAlreadyExists();
}