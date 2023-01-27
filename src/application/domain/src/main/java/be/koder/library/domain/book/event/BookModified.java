package be.koder.library.domain.book.event;

import be.koder.library.domain.event.Event;
import be.koder.library.vocabulary.book.BookId;

public record BookModified(BookId id) implements Event {}