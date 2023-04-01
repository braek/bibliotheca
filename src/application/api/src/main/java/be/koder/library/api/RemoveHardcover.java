package be.koder.library.api;

import be.koder.library.api.presenter.RemoveHardcoverPresenter;
import be.koder.library.vocabulary.book.BookId;

public interface RemoveHardcover {
    void removeHardcover(BookId bookId, RemoveHardcoverPresenter presenter);
}