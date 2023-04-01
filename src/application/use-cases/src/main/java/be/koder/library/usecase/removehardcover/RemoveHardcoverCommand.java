package be.koder.library.usecase.removehardcover;

import be.koder.library.domain.command.Command;
import be.koder.library.vocabulary.book.BookId;

public record RemoveHardcoverCommand(BookId bookId) implements Command {
}
