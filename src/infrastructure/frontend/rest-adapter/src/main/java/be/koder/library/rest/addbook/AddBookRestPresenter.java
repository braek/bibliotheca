package be.koder.library.rest.addbook;

import be.koder.library.api.presenter.AddBookPresenter;
import be.koder.library.rest.RestPresenter;
import be.koder.library.vocabulary.book.BookId;
import org.springframework.http.ResponseEntity;

public final class AddBookRestPresenter implements RestPresenter, AddBookPresenter {

    @Override
    public void added(BookId bookId) {

    }

    @Override
    public void isbnAlreadyExists() {

    }

    @Override
    public ResponseEntity<Object> getResponse() {
        return null;
    }
}