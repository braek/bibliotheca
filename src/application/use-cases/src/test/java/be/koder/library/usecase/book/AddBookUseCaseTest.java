package be.koder.library.usecase.book;

import be.koder.library.api.AddBook;
import be.koder.library.api.presenter.AddBookPresenter;
import be.koder.library.domain.book.Book;
import be.koder.library.domain.book.BookSnapshot;
import be.koder.library.domain.book.event.BookAdded;
import be.koder.library.test.BookObjectMother;
import be.koder.library.test.MockBookRepository;
import be.koder.library.test.MockEventPublisher;
import be.koder.library.usecase.addbook.AddBookUseCase;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Isbn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Given an API to add Books")
class AddBookUseCaseTest {

    private final MockBookRepository bookRepository = new MockBookRepository();
    private final MockEventPublisher eventPublisher = new MockEventPublisher();
    private final AddBook addBook = new AddBookUseCase(bookRepository, bookRepository, eventPublisher);

    @Nested
    @DisplayName("when Book is added successfully")
    class TestHappyFlow implements AddBookPresenter {

        private BookSnapshot book = BookObjectMother.INSTANCE.cleanCode;

        private boolean addedCalled;
        private BookId bookId;
        private BookSnapshot savedBook;

        @BeforeEach
        void setup() {
            addBook.addBook(book.isbn(), book.title(), book.author(), this);
            savedBook = bookRepository.get(bookId).map(Book::takeSnapshot).orElseThrow();
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(addedCalled);
            assertThat(bookId).isNotNull();
        }

        @Test
        @DisplayName("it should publish an event")
        void eventPublished() {
            assertThat(eventPublisher.getLastEvent()).hasValueSatisfying(it -> {
                assertThat(it).isInstanceOf(BookAdded.class);
                var event = (BookAdded) it;
                assertThat(event.bookId()).isEqualTo(bookId);
            });
        }

        @Test
        @DisplayName("it should save the Book")
        void bookSaved() {
            assertThat(savedBook.id()).isEqualTo(bookId);
            assertThat(savedBook.isbn()).isEqualTo(book.isbn());
            assertThat(savedBook.title()).isEqualTo(book.title());
            assertThat(savedBook.author()).isEqualTo(book.author());
            assertThat(savedBook.hardcover()).isEqualTo(book.hardcover());
        }

        @Override
        public void added(BookId bookId) {
            this.addedCalled = true;
            this.bookId = bookId;
        }

        @Override
        public void isbnAlreadyExists(Isbn isbn) {
            fail();
        }
    }

    @Nested
    @DisplayName("when ISBN already exists")
    class TestWhenIsbnAlreadyExists implements AddBookPresenter {

        private boolean isbnAlreadyExistsCalled;
        private BookSnapshot book = BookObjectMother.INSTANCE.cleanCode;
        private Isbn isbn;

        @BeforeEach
        void setup() {
            bookRepository.save(Book.fromSnapshot(book));
            addBook.addBook(book.isbn(), book.title(), book.author(), this);
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(isbnAlreadyExistsCalled);
            assertThat(isbn).isEqualTo(book.isbn());
        }

        @Override
        public void added(BookId bookId) {
            fail();
        }

        @Override
        public void isbnAlreadyExists(Isbn isbn) {
            this.isbnAlreadyExistsCalled = true;
            this.isbn = isbn;
        }
    }
}