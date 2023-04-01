package be.koder.library.api.presenter;

import be.koder.library.vocabulary.book.BookId;

import java.net.URL;

public interface RemoveHardcoverPresenter {

    void removed(BookId bookId, URL hardcover);

    void bookNotFound();

    void bookHasNoHardcover();
}