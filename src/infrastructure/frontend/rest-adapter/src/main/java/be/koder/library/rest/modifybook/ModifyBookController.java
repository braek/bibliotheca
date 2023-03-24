package be.koder.library.rest.modifybook;

import be.koder.library.api.ModifyBook;
import be.koder.library.vocabulary.book.Author;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Title;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ModifyBookController implements ModifyBookEndpoint {

    private final ModifyBook modifyBook;

    public ModifyBookController(final ModifyBook modifyBook) {
        this.modifyBook = modifyBook;
    }

    @Override
    public ResponseEntity<Object> modifyBook(final UUID bookId, final ModifyBookRequest request) {
        final var presenter = new ModifyBookRestPresenter();
        final var theBookId = BookId.fromUuid(bookId);
        final var title = Title.fromString(request.title());
        final var author = Author.fromString(request.author());
        modifyBook.modifyBook(theBookId, title, author, presenter);
        return presenter.getResponse();
    }
}