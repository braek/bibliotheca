package be.koder.library.rest.removebook;

import be.koder.library.api.presenter.RemoveBookPresenter;
import be.koder.library.rest.ErrorResponse;
import be.koder.library.rest.RestPresenter;
import be.koder.library.vocabulary.book.BookId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class RemoveBookRestPresenter implements RestPresenter, RemoveBookPresenter {

    private ResponseEntity<Object> response;

    @Override
    public void removed(BookId bookId) {
        response = ResponseEntity.status(HttpStatus.OK).body(new BookRemovedResponse(bookId.getValue()));
    }

    @Override
    public void bookNotFound() {
        response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Boek niet gevonden."));
    }

    @Override
    public ResponseEntity<Object> getResponse() {
        return response;
    }
}