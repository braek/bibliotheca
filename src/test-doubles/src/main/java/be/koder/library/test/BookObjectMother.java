package be.koder.library.test;

import be.koder.library.domain.book.BookSnapshot;
import be.koder.library.vocabulary.book.Author;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Isbn;
import be.koder.library.vocabulary.book.Title;

public enum BookObjectMother {

    INSTANCE;

    public final BookSnapshot cleanCode = new BookSnapshot(
            BookId.createNew(),
            Isbn.fromString("9780132350884"),
            Title.fromString("Clean Code"),
            Author.fromString("Robert C. Martin"),
            null
    );
}