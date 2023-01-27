package be.koder.library.api.presenter;

import be.koder.library.vocabulary.book.BookId;

public interface ModifyBookPresenter {

    void modified(BookId bookId);

    void bookNotFound();
}