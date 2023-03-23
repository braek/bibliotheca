package be.koder.library.rest.addbook;

import be.koder.library.api.AddBook;
import be.koder.library.vocabulary.book.Author;
import be.koder.library.vocabulary.book.Isbn;
import be.koder.library.vocabulary.book.Title;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddBookController implements AddBookEndpoint {

    private final AddBook addBook;

    public AddBookController(AddBook addBook) {
        this.addBook = addBook;
    }

    public ResponseEntity<Object> addBook(AddBookRequest request) {
        final var isbn = Isbn.fromString(request.isbn());
        final var title = Title.fromString(request.title());
        final var author = Author.fromString(request.author());
        final var presenter = new AddBookRestPresenter();
        addBook.addBook(isbn, title, author, presenter);
        return presenter.getResponse();
    }
}