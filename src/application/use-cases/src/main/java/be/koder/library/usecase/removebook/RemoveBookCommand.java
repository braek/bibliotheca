package be.koder.library.usecase.removebook;

import be.koder.library.domain.command.Command;
import be.koder.library.vocabulary.book.BookId;

public record RemoveBookCommand(BookId bookId) implements Command {
}