package be.koder.library.usecase.book;

import be.koder.library.api.presenter.AddBookPresenter;
import be.koder.library.domain.book.Book;
import be.koder.library.domain.book.BookSnapshot;
import be.koder.library.domain.book.event.BookAdded;
import be.koder.library.test.MockBookRepository;
import be.koder.library.test.MockEventPublisher;
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

@DisplayName("Given a use case to add Books")
class AddBookUseCaseTest {

    private final MockBookRepository bookRepository = new MockBookRepository();
    private final MockEventPublisher eventPublisher = new MockEventPublisher();
    private final AddBookUseCase useCase = new AddBookUseCase(bookRepository, bookRepository, eventPublisher);

    @Nested
    @DisplayName("when Book is added successfully")
    class TestHappyFlow implements AddBookPresenter {

        private final Title title = Title.fromString("Clean Code: A Handbook of Agile Software Craftsmanship");
        private final Author author = Author.fromString("Robert C. Martin");
        private final Isbn isbn = Isbn.fromString("1234567890");

        private boolean addedCalled;
        private BookId bookId;
        private BookSnapshot savedBook;

        @BeforeEach
        void setup() {
            useCase.addBook(isbn, title, author, this);
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
            assertThat(savedBook.isbn()).isEqualTo(isbn);
            assertThat(savedBook.title()).isEqualTo(title);
            assertThat(savedBook.author()).isEqualTo(author);
        }

        @Override
        public void added(BookId bookId) {
            this.addedCalled = true;
            this.bookId = bookId;
        }

        @Override
        public void isbnAlreadyExists() {
            fail();
        }
    }

    @Nested
    @DisplayName("when ISBN already exists")
    class TestWhenIsbnAlreadyExists implements AddBookPresenter {

        private boolean isbnAlreadyExistsCalled;
        private BookSnapshot book = new BookSnapshot(
                BookId.createNew(),
                Isbn.fromString("1234567890"),
                Title.fromString("The Title"),
                Author.fromString("The Author")
        );

        @BeforeEach
        void setup() {
            bookRepository.save(Book.fromSnapshot(book));
            useCase.addBook(book.isbn(), book.title(), book.author(), this);
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(isbnAlreadyExistsCalled);
        }

        @Override
        public void added(BookId bookId) {
            fail();
        }

        @Override
        public void isbnAlreadyExists() {
            this.isbnAlreadyExistsCalled = true;
        }
    }
}