package be.koder.library.usecase.book;

import be.koder.library.api.ModifyBook;
import be.koder.library.api.presenter.ModifyBookPresenter;
import be.koder.library.domain.book.Book;
import be.koder.library.domain.book.BookSnapshot;
import be.koder.library.domain.book.event.BookModified;
import be.koder.library.test.BookObjectMother;
import be.koder.library.test.MockBookRepository;
import be.koder.library.test.MockEventPublisher;
import be.koder.library.vocabulary.book.Author;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Title;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Given a use case to modify Books")
class ModifyBookTest {

    private final MockBookRepository bookRepository = new MockBookRepository();
    private final MockEventPublisher eventPublisher = new MockEventPublisher();
    private final ModifyBook modifyBook = new ModifyBookUseCase(bookRepository, eventPublisher);

    @Nested
    @DisplayName("when Book is modified successfully")
    class TestHappyFlow implements ModifyBookPresenter {

        private final BookSnapshot book = BookObjectMother.INSTANCE.cleanCode;
        private final Title newTitle = Title.fromString("The New Title");
        private final Author newAuthor = Author.fromString("The New Author");
        private boolean modifiedCalled;
        private BookId modifiedBookId;
        private BookSnapshot modifiedBook;

        @BeforeEach
        void setup() {
            bookRepository.save(Book.fromSnapshot(book));
            modifyBook.modifyBook(book.id(), newTitle, newAuthor, this);
            modifiedBook = bookRepository.get(book.id()).map(Book::takeSnapshot).orElseThrow();
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
        @DisplayName("it should save the Book")
        void bookSaved() {
            assertThat(modifiedBook.id()).isEqualTo(book.id());
            assertThat(modifiedBook.isbn()).isEqualTo(book.isbn());
            assertThat(modifiedBook.title()).isEqualTo(newTitle);
            assertThat(modifiedBook.author()).isEqualTo(newAuthor);
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

    @Nested
    @DisplayName("when non-existing Book is modified")
    class TestWhenNonExistingBookModified implements ModifyBookPresenter {

        private boolean bookNotFoundCalled;

        @BeforeEach
        void setup() {
            modifyBook.modifyBook(BookId.createNew(), Title.fromString("The Title"), Author.fromString("The Author"), this);
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(bookNotFoundCalled);
        }

        @Override
        public void modified(BookId bookId) {
            fail();
        }

        @Override
        public void bookNotFound() {
            bookNotFoundCalled = true;
        }
    }
}