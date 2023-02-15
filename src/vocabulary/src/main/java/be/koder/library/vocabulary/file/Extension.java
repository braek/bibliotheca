package be.koder.library.vocabulary.file;

import java.util.Objects;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

public final class Extension {

    private final String value;

    private Extension(final String value) {
        var sanitized = ofNullable(value)
                .map(String::trim)
                .map(String::toLowerCase)
                .orElse(null);
        if (isNull(sanitized) || !Pattern.compile("^[a-z]{2,4}$").matcher(sanitized).matches()) {
            throw new InvalidExtensionException(value);
        }
        this.value = value;
    }

    public static Extension fromString(final String str) {
        return new Extension(str);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Extension extension = (Extension) o;
        return Objects.equals(value, extension.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}