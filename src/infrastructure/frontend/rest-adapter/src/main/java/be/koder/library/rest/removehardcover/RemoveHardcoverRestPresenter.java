package be.koder.library.rest.removehardcover;

import be.koder.library.api.presenter.RemoveHardcoverPresenter;
import be.koder.library.rest.ErrorResponse;
import be.koder.library.rest.RestPresenter;
import be.koder.library.rest.removebook.BookRemovedResponse;
import be.koder.library.vocabulary.book.BookId;
import org.springframework.http.HttpStatus;

import java.net.URL;

public final class RemoveHardcoverRestPresenter extends RestPresenter implements RemoveHardcoverPresenter {

    @Override
    public void removed(BookId bookId, URL hardcover) {
        setResponse(HttpStatus.OK, new BookRemovedResponse(bookId.getValue()));
    }

    @Override
    public void bookNotFound() {
        setResponse(HttpStatus.NOT_FOUND, new ErrorResponse("Boek bestaat niet."));
    }

    @Override
    public void bookHasNoHardcover() {
        setResponse(HttpStatus.BAD_REQUEST, new ErrorResponse("Boek heeft geen hardcover."));
    }
}