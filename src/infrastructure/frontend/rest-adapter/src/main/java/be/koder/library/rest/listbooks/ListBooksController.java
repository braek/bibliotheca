package be.koder.library.rest.listbooks;

import be.koder.library.api.ListBooks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ListBooksController implements ListBooksEndpoint {

    private final ListBooks listBooks;

    public ListBooksController(ListBooks listBooks) {
        this.listBooks = listBooks;
    }

    @Override
    public ResponseEntity<List<ListBooksItem>> listBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(listBooks.listBooks().stream()
                .map(it -> new ListBooksItem(
                        it.id().getValue(),
                        it.isbn().toString(),
                        it.title().toString(),
                        it.author().toString()
                )).toList());
    }
}