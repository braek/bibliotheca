package be.koder.library.rest.modifybook;

import be.koder.library.api.presenter.ModifyBookPresenter;
import be.koder.library.rest.ErrorResponse;
import be.koder.library.rest.RestPresenter;
import be.koder.library.vocabulary.book.BookId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ModifyBookRestPresenter extends RestPresenter implements ModifyBookPresenter {

    @Override
    public void modified(BookId bookId) {
        response = ResponseEntity.status(HttpStatus.OK).body(new BookModifiedResponse(bookId.getValue()));
    }

    @Override
    public void bookNotFound() {
        response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Book does not exist."));
    }
}