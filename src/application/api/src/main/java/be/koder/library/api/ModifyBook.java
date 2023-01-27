package be.koder.library.api;

import be.koder.library.api.presenter.ModifyBookPresenter;
import be.koder.library.vocabulary.book.Author;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Title;

public interface ModifyBook {
    void modifyBook(BookId bookId, Title title, Author author, ModifyBookPresenter presenter);
}