package be.koder.library.usecase.sethardcover;

import be.koder.library.domain.command.Command;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.file.Filename;

public record SetHardcoverCommand(BookId bookId, Filename filename, byte[] data) implements Command {
}