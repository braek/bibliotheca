package be.koder.library.api.presenter;

import be.koder.library.vocabulary.book.BookId;

public interface RemoveBookPresenter {

    void removed(BookId bookId);

    void bookNotFound();
}