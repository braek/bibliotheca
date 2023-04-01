package be.koder.library.rest.removehardcover;

import be.koder.library.api.RemoveHardcover;
import be.koder.library.vocabulary.book.BookId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RemoveHardcoverController implements RemoveHardcoverEndpoint {

    private final RemoveHardcover removeHardcover;

    public RemoveHardcoverController(RemoveHardcover removeHardcover) {
        this.removeHardcover = removeHardcover;
    }

    @Override
    public ResponseEntity<Object> removeHardcover(UUID bookId) {
        var presenter = new RemoveHardcoverRestPresenter();
        removeHardcover.removeHardcover(BookId.fromUuid(bookId), presenter);
        return presenter.getResponse();
    }
}