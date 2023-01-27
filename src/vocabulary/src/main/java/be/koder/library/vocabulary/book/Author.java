package be.koder.library.vocabulary.book;

import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

public final class Author {

    private final String value;

    private Author(String value) {
        var sanitized = ofNullable(value)
                .map(String::trim)
                .orElse(null);
        if (isNull(sanitized) || sanitized.length() == 0 || sanitized.length() > 100) {
            throw new InvalidAuthorException(value);
        }
        this.value = value;
    }

    public static Author fromString(final String str) {
        return new Author(str);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(value, author.value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
