package be.koder.library.domain.book.event;

import be.koder.library.vocabulary.book.BookId;

import java.net.URL;

public interface RemoveHardcoverEventPublisher {

    void removed(BookId bookId, URL hardcover);

    void bookHasNoHardcover();
}