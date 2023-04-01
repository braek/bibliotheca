package be.koder.library.rest.removebook;

import be.koder.library.api.presenter.RemoveBookPresenter;
import be.koder.library.rest.ErrorResponse;
import be.koder.library.rest.RestPresenter;
import be.koder.library.vocabulary.book.BookId;
import org.springframework.http.HttpStatus;

public final class RemoveBookRestPresenter extends RestPresenter implements RemoveBookPresenter {

    @Override
    public void removed(BookId bookId) {
        setResponse(HttpStatus.OK, new BookRemovedResponse(bookId.getValue()));
    }

    @Override
    public void bookNotFound() {
        setResponse(HttpStatus.NOT_FOUND, new ErrorResponse("Boek niet gevonden."));
    }
}