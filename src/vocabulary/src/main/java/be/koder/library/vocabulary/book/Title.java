package be.koder.library.vocabulary.book;

import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

public final class Title {

    private final String value;

    private Title(final String value) {
        var sanitized = ofNullable(value)
                .map(String::trim)
                .orElse(null);
        if (isNull(sanitized) || sanitized.length() == 0 || sanitized.length() > 100) {
            throw new InvalidTitleException(value);
        }
        this.value = value;
    }

    public static Title fromString(final String str) {
        return new Title(str);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Title title = (Title) o;
        return value.equals(title.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}