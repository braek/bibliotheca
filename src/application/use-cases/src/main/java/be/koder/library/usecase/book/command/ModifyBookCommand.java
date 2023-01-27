package be.koder.library.usecase.book.command;

import be.koder.library.domain.command.Command;
import be.koder.library.vocabulary.book.Author;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Title;

public record ModifyBookCommand(BookId bookId, Title title, Author author) implements Command {
}