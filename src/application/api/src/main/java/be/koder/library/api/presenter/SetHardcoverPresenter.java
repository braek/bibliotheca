package be.koder.library.api.presenter;

import be.koder.library.vocabulary.book.BookId;

import java.net.URL;

public interface SetHardcoverPresenter {

    void set(BookId bookId, URL hardcover);

    void fileExtensionNotAllowed();

    void fileEmpty();

    void fileTooLarge();

    void bookNotFound();

    void storageFailed(String reason);
}