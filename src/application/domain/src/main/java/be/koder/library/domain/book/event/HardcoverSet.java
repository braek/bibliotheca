package be.koder.library.domain.book.event;

import be.koder.library.domain.event.Event;
import be.koder.library.vocabulary.book.BookId;

import java.net.URL;

public record HardcoverSet(BookId bookId, URL hardcover) implements Event {
}