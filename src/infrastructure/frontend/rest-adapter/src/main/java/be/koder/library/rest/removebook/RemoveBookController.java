package be.koder.library.rest.removebook;

import be.koder.library.api.RemoveBook;
import be.koder.library.vocabulary.book.BookId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RemoveBookController implements RemoveBookEndpoint {

    private final RemoveBook removeBook;

    public RemoveBookController(final RemoveBook removeBook) {
        this.removeBook = removeBook;
    }

    @Override
    public ResponseEntity<Object> removeBook(final UUID bookId) {
        var presenter = new RemoveBookRestPresenter();
        removeBook.removeBook(BookId.fromUuid(bookId), presenter);
        return presenter.getResponse();
    }
}