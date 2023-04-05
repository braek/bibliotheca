package be.koder.library.rest.addbook;

import be.koder.library.api.presenter.AddBookPresenter;
import be.koder.library.rest.ErrorResponse;
import be.koder.library.rest.RestPresenter;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Isbn;
import org.springframework.http.HttpStatus;

public final class AddBookRestPresenter extends RestPresenter implements AddBookPresenter {

    @Override
    public void added(BookId bookId) {
        setResponse(HttpStatus.CREATED, new BookAddedResponse(bookId.getValue()));
    }

    @Override
    public void isbnAlreadyExists(Isbn isbn) {
        setResponse(HttpStatus.BAD_REQUEST, new ErrorResponse("Het ISBN bestaat al."));
    }
}