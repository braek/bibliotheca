package be.koder.library.rest.addbook;

import be.koder.library.api.presenter.AddBookPresenter;
import be.koder.library.rest.ErrorResponse;
import be.koder.library.rest.RestPresenter;
import be.koder.library.vocabulary.book.BookId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class AddBookRestPresenter extends RestPresenter implements AddBookPresenter {

    @Override
    public void added(BookId bookId) {
        response = ResponseEntity.status(HttpStatus.CREATED).body(new BookAddedResponse(bookId.getValue()));
    }

    @Override
    public void isbnAlreadyExists() {
        response = ResponseEntity.badRequest().body(new ErrorResponse("Het ISBN bestaat al."));
    }
}