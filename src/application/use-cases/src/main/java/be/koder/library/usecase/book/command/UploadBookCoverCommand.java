package be.koder.library.usecase.book.command;

import be.koder.library.domain.command.Command;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.file.Filename;

public record UploadBookCoverCommand(BookId bookId, Filename filename, byte[] data) implements Command {
}