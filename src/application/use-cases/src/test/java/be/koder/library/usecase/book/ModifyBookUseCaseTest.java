package be.koder.library.usecase.book;

import be.koder.library.api.presenter.ModifyBookPresenter;
import be.koder.library.domain.book.Book;
import be.koder.library.domain.book.BookSnapshot;
import be.koder.library.domain.book.event.BookModified;
import be.koder.library.test.MockBookRepository;
import be.koder.library.test.MockEventPublisher;
import be.koder.library.usecase.book.command.ModifyBookCommand;
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

@DisplayName("Given a use case to modify Books")
class ModifyBookUseCaseTest {

    private final MockBookRepository bookRepository = new MockBookRepository();
    private final MockEventPublisher eventPublisher = new MockEventPublisher();
    private final ModifyBookUseCase useCase = new ModifyBookUseCase(bookRepository, eventPublisher);

    @Nested
    @DisplayName("when Book is modified successfully")
    class TestHappyFlow implements ModifyBookPresenter {

        private final BookSnapshot book = new BookSnapshot(
                BookId.createNew(),
                Isbn.fromString("1234567890"),
                Title.fromString("The Title"),
                Author.fromString("The Author")
        );
        private final Title newTitle = Title.fromString("The New Title");
        private final Author newAuthor = Author.fromString("The New Author");
        private boolean modifiedCalled;
        private BookId modifiedBookId;

        @BeforeEach
        void setup() {
            bookRepository.save(Book.fromSnapshot(book));
            useCase.execute(new ModifyBookCommand(book.id(), newTitle, newAuthor), this);
        }

        @Test
        @DisplayName("it should publish an event")
        void eventPublished() {
            assertThat(eventPublisher.getLastEvent()).hasValueSatisfying(it -> {
                assertThat(it).isInstanceOf(BookModified.class);
                var event = (BookModified) it;
                assertThat(event.bookId()).isEqualTo(book.id());
            });
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(modifiedCalled);
            assertThat(modifiedBookId).isEqualTo(book.id());
        }

        @Override
        public void modified(BookId bookId) {
            this.modifiedCalled = true;
            this.modifiedBookId = bookId;
        }

        @Override
        public void bookNotFound() {
            fail();
        }
    }
}