package be.koder.library.rest.modifybook;

import be.koder.library.api.presenter.ModifyBookPresenter;
import be.koder.library.rest.RestPresenter;
import be.koder.library.vocabulary.book.BookId;
import org.springframework.http.ResponseEntity;

public final class ModifyBookRestPresenter implements ModifyBookPresenter, RestPresenter {

    private ResponseEntity<Object> response;

    @Override
    public void modified(BookId bookId) {

    }

    @Override
    public void bookNotFound() {

    }

    @Override
    public ResponseEntity<Object> getResponse() {
        return response;
    }
}