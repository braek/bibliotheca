package be.koder.library.domain.book.event;

import be.koder.library.vocabulary.book.BookId;

import java.net.URL;

public interface SetHardcoverEventPublisher {

    void set(BookId bookId, URL hardcover);

    void storageFailed(String reason);

    void fileExtensionNotAllowed();

    void fileEmpty();

    void fileTooLarge();
}