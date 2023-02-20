package be.koder.library.api;

import be.koder.library.api.presenter.SetHardcoverPresenter;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.file.Filename;

public interface SetHardcover {
    void setHardcover(BookId bookId, Filename filename, byte[] data, SetHardcoverPresenter presenter);
}