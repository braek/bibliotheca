package be.koder.library.rest.sethardcover;

import be.koder.library.api.SetHardcover;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.file.Filename;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class SetHardcoverController implements SetHardcoverEndpoint {

    private final SetHardcover setHardcover;

    public SetHardcoverController(final SetHardcover setHardcover) {
        this.setHardcover = setHardcover;
    }

    @Override
    public ResponseEntity<Object> setHardcover(UUID bookId, MultipartFile hardcover) throws IOException {
        var presenter = new SetHardcoverRestPresenter();
        final var theBookId = BookId.fromUuid(bookId);
        final var filename = Filename.fromString(hardcover.getOriginalFilename());
        final var data = hardcover.getInputStream().readAllBytes();
        setHardcover.setHardcover(theBookId, filename, data, presenter);
        return presenter.getResponse();
    }
}