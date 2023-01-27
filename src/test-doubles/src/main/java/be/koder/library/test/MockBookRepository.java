package be.koder.library.test;

import be.koder.library.domain.book.Book;
import be.koder.library.domain.book.BookRepository;
import be.koder.library.domain.book.BookSnapshot;
import be.koder.library.domain.book.service.IsbnService;
import be.koder.library.query.book.BookArchive;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Isbn;
import be.koder.library.vocabulary.dto.BookListItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class MockBookRepository implements BookRepository, IsbnService, BookArchive {

    private final Map<BookId, BookSnapshot> store = new HashMap<>();

    @Override
    public void save(Book book) {
        final var snapshot = book.takeSnapshot();
        store.put(snapshot.id(), snapshot);
    }

    @Override
    public Optional<Book> get(BookId id) {
        if (store.containsKey(id)) {
            return Optional.of(Book.fromSnapshot(store.get(id)));
        }
        return Optional.empty();
    }

    @Override
    public void delete(BookId id) {
        store.remove(id);
    }

    @Override
    public boolean exists(Isbn isbn) {
        return store.values().stream().anyMatch(it -> it.isbn().equals(isbn));
    }

    @Override
    public List<BookListItem> listBooks() {
        return store.values().stream().map(it -> new BookListItem(
                it.id(),
                it.isbn(),
                it.title(),
                it.author()
        )).toList();
    }
}