package be.koder.library.vocabulary.book;

import java.util.Objects;
import java.util.UUID;

public final class BookId {

    private final UUID value;

    private BookId(final UUID value) {
        this.value = value;
    }

    public static BookId createNew() {
        return new BookId(UUID.randomUUID());
    }

    public UUID getValue() {
        return value;
    }

    public static BookId fromUuid(final UUID uuid) {
        return new BookId(uuid);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookId bookId = (BookId) o;
        return value.equals(bookId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}