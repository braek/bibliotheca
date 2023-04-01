package be.koder.library.rest.sethardcover;

import be.koder.library.api.presenter.SetHardcoverPresenter;
import be.koder.library.rest.ErrorResponse;
import be.koder.library.rest.RestPresenter;
import be.koder.library.vocabulary.book.BookId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;

public final class SetHardcoverRestPresenter extends RestPresenter implements SetHardcoverPresenter {

    @Override
    public void set(BookId bookId, URL hardcover) {
        setResponse(ResponseEntity.status(HttpStatus.OK).body(new HardcoverSetResponse(bookId.getValue(), hardcover)));
    }

    @Override
    public void storageFailed(String reason) {
        setResponse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Opslagen van hardcover is mislukt.")));
    }

    @Override
    public void fileExtensionNotAllowed() {
        setResponse(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Bestand type is niet toegelaten.")));
    }

    @Override
    public void fileEmpty() {
        setResponse(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Bestand is leeg.")));
    }

    @Override
    public void fileTooLarge() {
        setResponse(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Bestand is te groot.")));

    }

    @Override
    public void bookNotFound() {
        setResponse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Boek bestaat niet.")));
    }
}