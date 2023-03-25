package be.koder.library.domain.book;

import be.koder.library.domain.book.event.BookAdded;
import be.koder.library.domain.book.event.BookModified;
import be.koder.library.domain.book.event.SetHardcoverEventPublisher;
import be.koder.library.domain.event.EventPublisher;
import be.koder.library.vocabulary.book.Author;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Isbn;
import be.koder.library.vocabulary.book.Title;
import be.koder.library.vocabulary.file.Extension;
import be.koder.library.vocabulary.file.Filename;

import java.net.URL;
import java.util.List;

// Book = aggregate root
public final class Book {

    private final BookId id;
    private final Isbn isbn;
    private Title title;
    private Author author;
    private URL hardcover;

    private Book(final BookId id, final Isbn isbn, final Title title, final Author author, final URL hardcover) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.hardcover = hardcover;
    }

    public static Book createNew(final Isbn isbn, final Title title, final Author author, final EventPublisher eventPublisher) {
        final var book = new Book(BookId.createNew(), isbn, title, author, null);
        eventPublisher.publish(new BookAdded(book.id));
        return book;
    }

    public void modify(final Title title, final Author author, final EventPublisher eventPublisher) {
        this.title = title;
        this.author = author;
        eventPublisher.publish(new BookModified(this.id));
    }

    public BookSnapshot takeSnapshot() {
        return new BookSnapshot(
                this.id,
                this.isbn,
                this.title,
                this.author,
                this.hardcover
        );
    }

    public static Book fromSnapshot(final BookSnapshot snapshot) {
        return new Book(
                snapshot.id(),
                snapshot.isbn(),
                snapshot.title(),
                snapshot.author(),
                snapshot.hardcover()
        );
    }

    public void setHardcover(final Filename filename, final byte[] data, final HardcoverStore hardcoverStore, final SetHardcoverEventPublisher eventPublisher) {
        if (!List.of(
                Extension.fromString("gif"),
                Extension.fromString("jpe"),
                Extension.fromString("jpeg"),
                Extension.fromString("jpg"),
                Extension.fromString("png")
        ).contains(filename.getExtension())) {
            eventPublisher.fileExtensionNotAllowed();
            return;
        }
        if (data.length == 0) {
            eventPublisher.fileEmpty();
            return;
        }
        final var maxFileSize = 2 * 1000 * 1000;
        if (data.length > maxFileSize) {
            eventPublisher.fileTooLarge();
            return;
        }
        var response = hardcoverStore.store(String.format("%s.%s", this.id, filename.getExtension()), data);
        if (response.isNok()) {
            eventPublisher.storageFailed(response.getError());
            return;
        }
        this.hardcover = response.getUrl();
        eventPublisher.set(this.id, this.hardcover);
    }
}