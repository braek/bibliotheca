package be.koder.library.usecase.book;

import be.koder.library.api.RemoveHardcover;
import be.koder.library.api.presenter.RemoveHardcoverPresenter;
import be.koder.library.domain.book.Book;
import be.koder.library.domain.book.BookRepository;
import be.koder.library.domain.book.BookSnapshot;
import be.koder.library.domain.book.event.HardcoverRemoved;
import be.koder.library.test.MockBookRepository;
import be.koder.library.test.MockEventPublisher;
import be.koder.library.usecase.removehardcover.RemoveHardcoverUseCase;
import be.koder.library.vocabulary.book.Author;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Isbn;
import be.koder.library.vocabulary.book.Title;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Given a use case to remove hardcover")
public class RemoveHardcoverUseCaseTest {

    private final BookRepository bookRepository = new MockBookRepository();
    private final MockEventPublisher eventPublisher = new MockEventPublisher();
    private final RemoveHardcover removeHardcover = new RemoveHardcoverUseCase(bookRepository, eventPublisher);

    @Nested
    @DisplayName("when Hardcover removed from Book with Hardcover")
    class TestHappyFlow implements RemoveHardcoverPresenter {

        private final BookSnapshot bookWithHardcover = new BookSnapshot(
                BookId.createNew(),
                Isbn.fromString("1112223334"),
                Title.fromString("The Book Title"),
                Author.fromString("The Book Author"),
                new URL("https://library.aws.com/book.jpg")
        );
        private boolean removedCalled;
        private URL removedHardcover;
        private Book updatedBook;

        TestHappyFlow() throws MalformedURLException {
        }

        @BeforeEach
        void setup() {
            bookRepository.save(Book.fromSnapshot(bookWithHardcover));
            removeHardcover.removeHardcover(bookWithHardcover.id(), this);
            updatedBook = bookRepository.get(bookWithHardcover.id()).orElseThrow();
        }

        @Test
        @DisplayName("it should publish an event")
        void eventPublished() {
            assertThat(eventPublisher.getLastEvent()).hasValueSatisfying(it -> {
                assertThat(it).isInstanceOf(HardcoverRemoved.class);
                var event = (HardcoverRemoved) it;
                assertThat(event.bookId()).isEqualTo(bookWithHardcover.id());
                assertThat(event.hardcover()).isEqualTo(bookWithHardcover.hardcover());
            });
        }

        @Test
        @DisplayName("it should be saved")
        void updateSaved() {
            assertThat(updatedBook.takeSnapshot().hardcover()).isNull();
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(removedCalled);
            assertThat(removedHardcover).isEqualTo(bookWithHardcover.hardcover());
        }

        @Override
        public void removed(BookId bookId, URL hardcover) {
            this.removedCalled = true;
            this.removedHardcover = hardcover;
        }

        @Override
        public void bookNotFound() {
            fail();
        }

        @Override
        public void bookHasNoHardcover() {
            fail();
        }
    }

    @Nested
    @DisplayName("when Hardcover removed from Book without Hardcover")
    class TestWhenBookHasNoHardcover implements RemoveHardcoverPresenter {

        private final BookSnapshot bookWithHardcover = new BookSnapshot(
                BookId.createNew(),
                Isbn.fromString("1112223334"),
                Title.fromString("The Book Title"),
                Author.fromString("The Book Author"),
                null
        );
        private boolean bookHasNoHardcoverCalled;

        @BeforeEach
        void setup() {
            bookRepository.save(Book.fromSnapshot(bookWithHardcover));
            removeHardcover.removeHardcover(bookWithHardcover.id(), this);
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(bookHasNoHardcoverCalled);
        }

        @Override
        public void removed(BookId bookId, URL hardcover) {
            fail();
        }

        @Override
        public void bookNotFound() {
            fail();
        }

        @Override
        public void bookHasNoHardcover() {
            this.bookHasNoHardcoverCalled = true;
        }
    }

    @Nested
    @DisplayName("when Hardcover removed from non-existing Book")
    class TestWhenNonExistingBook implements RemoveHardcoverPresenter {

        private boolean bookNotFoundCalled;

        @BeforeEach
        void setup() {
            removeHardcover.removeHardcover(BookId.createNew(), this);
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(bookNotFoundCalled);
        }

        @Override
        public void removed(BookId bookId, URL hardcover) {
            fail();
        }

        @Override
        public void bookNotFound() {
            this.bookNotFoundCalled = true;
        }

        @Override
        public void bookHasNoHardcover() {
            fail();
        }
    }
}