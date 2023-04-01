package be.koder.library.rest.modifybook;

import be.koder.library.api.presenter.ModifyBookPresenter;
import be.koder.library.rest.ErrorResponse;
import be.koder.library.rest.RestPresenter;
import be.koder.library.vocabulary.book.BookId;
import org.springframework.http.HttpStatus;

public final class ModifyBookRestPresenter extends RestPresenter implements ModifyBookPresenter {

    @Override
    public void modified(BookId bookId) {
        setResponse(HttpStatus.OK, new BookModifiedResponse(bookId.getValue()));
    }

    @Override
    public void bookNotFound() {
        setResponse(HttpStatus.NOT_FOUND, new ErrorResponse("Book does not exist."));
    }
}