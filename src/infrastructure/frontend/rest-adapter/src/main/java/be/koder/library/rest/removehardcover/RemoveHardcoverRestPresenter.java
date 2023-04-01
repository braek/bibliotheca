package be.koder.library.rest.removehardcover;

import be.koder.library.api.presenter.RemoveHardcoverPresenter;
import be.koder.library.rest.ErrorResponse;
import be.koder.library.rest.RestPresenter;
import be.koder.library.rest.removebook.BookRemovedResponse;
import be.koder.library.vocabulary.book.BookId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;

public final class RemoveHardcoverRestPresenter extends RestPresenter implements RemoveHardcoverPresenter {

    @Override
    public void removed(BookId bookId, URL hardcover) {
        response = ResponseEntity.status(HttpStatus.OK).body(new BookRemovedResponse(bookId.getValue()));
    }

    @Override
    public void bookNotFound() {
        response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Boek bestaat niet."));
    }

    @Override
    public void bookHasNoHardcover() {
        response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Boek heeft geen hardcover."));
    }
}