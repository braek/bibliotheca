package be.koder.library.api;

import be.koder.library.api.presenter.RemoveBookPresenter;
import be.koder.library.vocabulary.book.BookId;

public interface RemoveBook {
    void removeBook(BookId bookId, RemoveBookPresenter presenter);
}
