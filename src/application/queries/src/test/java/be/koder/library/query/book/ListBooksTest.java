package be.koder.library.query.book;

import be.koder.library.api.ListBooks;
import be.koder.library.domain.book.Book;
import be.koder.library.test.BookObjectMother;
import be.koder.library.test.MockBookRepository;
import be.koder.library.vocabulary.dto.BookListItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Given an API to list Books")
class ListBooksTest {

    private final MockBookRepository bookRepository = new MockBookRepository();
    private final ListBooks listBooks = new ListBooksQuery(bookRepository);

    @Nested
    @DisplayName("when listing of Books is requested")
    class TestHappyFlow {

        private final List<BookListItem> books = new ArrayList<>();

        @BeforeEach
        void setup() {
            bookRepository.save(Book.fromSnapshot(BookObjectMother.INSTANCE.cleanCode));
            books.addAll(listBooks.listBooks());
        }

        @Test
        @DisplayName("it should contain items")
        void itemsReturned() {
            assertThat(books).containsAll(List.of(
                    new BookListItem(
                            BookObjectMother.INSTANCE.cleanCode.id(),
                            BookObjectMother.INSTANCE.cleanCode.isbn(),
                            BookObjectMother.INSTANCE.cleanCode.title(),
                            BookObjectMother.INSTANCE.cleanCode.author(),
                            BookObjectMother.INSTANCE.cleanCode.hardcover()
                    )
            ));
        }
    }
}