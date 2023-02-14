package be.koder.library.domain.book.event;

import be.koder.library.vocabulary.book.BookId;

import java.net.URL;

public interface UploadHardcoverEventPublisher {

    void uploaded(BookId bookId, URL hardcover);

    void uploadFailed(String reason);

    void fileExtensionNotAllowed();

    void fileEmpty();

    void fileTooLarge();
}