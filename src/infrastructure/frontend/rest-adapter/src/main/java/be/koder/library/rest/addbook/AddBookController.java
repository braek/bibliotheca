package be.koder.library.rest.addbook;

import be.koder.library.api.AddBook;
import be.koder.library.api.presenter.AddBookPresenter;
import be.koder.library.rest.ErrorResponse;
import be.koder.library.vocabulary.book.Author;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Isbn;
import be.koder.library.vocabulary.book.Title;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1")
public class AddBookController implements AddBookPresenter {

    private final AddBook addBook;
    private ResponseEntity<Object> response;

    public AddBookController(AddBook addBook) {
        this.addBook = addBook;
    }

    @PostMapping("/books")
    public ResponseEntity<Object> addBook(AddBookRequest request) {
        final var isbn = Isbn.fromString(request.isbn());
        final var title = Title.fromString(request.title());
        final var author = Author.fromString(request.author());
        final var presenter = new AddBookRestPresenter();
        addBook.addBook(isbn, title, author, presenter);
        return presenter.getResponse();
    }

    @Override
    public void added(BookId bookId) {
        response = ResponseEntity.status(HttpStatus.CREATED).body(new AddBookResponse(bookId.getValue()));
    }

    @Override
    public void isbnAlreadyExists() {
        response = ResponseEntity.badRequest().body(new ErrorResponse("Het ISBN bestaat al."));
    }
}