package be.koder.library.usecase.book;

import be.koder.library.api.presenter.RemoveBookPresenter;
import be.koder.library.domain.book.Book;
import be.koder.library.domain.book.BookSnapshot;
import be.koder.library.domain.book.event.BookRemoved;
import be.koder.library.test.MockBookRepository;
import be.koder.library.test.MockEventPublisher;
import be.koder.library.usecase.book.command.RemoveBookCommand;
import be.koder.library.vocabulary.book.Author;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Isbn;
import be.koder.library.vocabulary.book.Title;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Given a use case to remove Books")
class RemoveBookUseCaseTest {

    private final MockBookRepository bookRepository = new MockBookRepository();
    private final MockEventPublisher eventPublisher = new MockEventPublisher();
    private final RemoveBookUseCase useCase = new RemoveBookUseCase(bookRepository, eventPublisher);

    @Nested
    @DisplayName("when Book is removed successfully")
    class TestHappyFlow implements RemoveBookPresenter {

        private final BookId bookId = BookId.createNew();
        private boolean removedCalled;
        private BookId removedBookId;

        @BeforeEach
        void setup() {
            bookRepository.save(Book.fromSnapshot(new BookSnapshot(
                    bookId,
                    Isbn.fromString("0123456789"),
                    Title.fromString("The Title"),
                    Author.fromString("The Author")
            )));
            useCase.execute(new RemoveBookCommand(bookId), this);
        }

        @Test
        @DisplayName("it should publish an event")
        void eventPublished() {
            assertThat(eventPublisher.getLastEvent()).hasValueSatisfying(it -> {
                assertThat(it).isInstanceOf(BookRemoved.class);
                var event = (BookRemoved) it;
                assertThat(event.bookId()).isEqualTo(bookId);
            });
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(removedCalled);
            assertThat(removedBookId).isEqualTo(bookId);
        }

        @Test
        @DisplayName("it should no longer exist")
        void bookNoLongerExists() {
            assertThat(bookRepository.get(bookId)).isEmpty();
        }

        @Override
        public void removed(BookId bookId) {
            this.removedCalled = true;
            this.removedBookId = bookId;
        }

        @Override
        public void bookNotFound() {
            fail();
        }
    }

    @Nested
    @DisplayName("when non-existing Book is removed")
    class TestWhenNonExistingBookRemoved implements RemoveBookPresenter {

        private boolean bookNotFoundCalled;

        @BeforeEach
        void setup() {
            useCase.execute(new RemoveBookCommand(BookId.createNew()), this);
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(bookNotFoundCalled);
        }

        @Override
        public void removed(BookId bookId) {
            fail();
        }

        @Override
        public void bookNotFound() {
            bookNotFoundCalled = true;
        }
    }
}