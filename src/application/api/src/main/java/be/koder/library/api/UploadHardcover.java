package be.koder.library.api;

import be.koder.library.api.presenter.UploadHardcoverPresenter;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.file.Filename;

public interface UploadHardcover {
    void uploadHardcover(BookId bookId, Filename filename, byte[] data, UploadHardcoverPresenter presenter);
}