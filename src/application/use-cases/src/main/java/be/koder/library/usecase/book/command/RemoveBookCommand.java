package be.koder.library.usecase.book.command;

import be.koder.library.domain.command.Command;
import be.koder.library.vocabulary.book.BookId;

public record RemoveBookCommand(BookId bookId) implements Command {
}