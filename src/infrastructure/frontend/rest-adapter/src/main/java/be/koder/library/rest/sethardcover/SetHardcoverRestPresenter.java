package be.koder.library.rest.sethardcover;

import be.koder.library.api.presenter.SetHardcoverPresenter;
import be.koder.library.rest.ErrorResponse;
import be.koder.library.rest.RestPresenter;
import be.koder.library.vocabulary.book.BookId;
import org.springframework.http.HttpStatus;

import java.net.URL;

public final class SetHardcoverRestPresenter extends RestPresenter implements SetHardcoverPresenter {

    @Override
    public void set(BookId bookId, URL hardcover) {
        setResponse(HttpStatus.OK, new HardcoverSetResponse(bookId.getValue(), hardcover));
    }

    @Override
    public void storageFailed(String reason) {
        setResponse(HttpStatus.INTERNAL_SERVER_ERROR, new ErrorResponse("Opslagen van hardcover is mislukt."));
    }

    @Override
    public void fileExtensionNotAllowed() {
        setResponse(HttpStatus.BAD_REQUEST, new ErrorResponse("Bestand type is niet toegelaten."));
    }

    @Override
    public void fileEmpty() {
        setResponse(HttpStatus.BAD_REQUEST, new ErrorResponse("Bestand is leeg."));
    }

    @Override
    public void fileTooLarge() {
        setResponse(HttpStatus.BAD_REQUEST, new ErrorResponse("Bestand is te groot."));

    }

    @Override
    public void bookNotFound() {
        setResponse(HttpStatus.NOT_FOUND, new ErrorResponse("Boek bestaat niet."));
    }
}