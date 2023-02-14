package be.koder.library.vocabulary.file;

import java.util.Objects;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

public final class Filename {

    private final String value;
    private final String extension;

    private Filename(final String str) {
        final var sanitized = ofNullable(str)
                .map(String::trim)
                .orElse(null);
        if (isNull(sanitized) || !Pattern.compile("^\\w+\\.[a-z0-9]{3,4}$").matcher(sanitized).matches()) {
            throw new InvalidFilenameException(str);
        }
        this.value = sanitized;
        final var chunks = sanitized.split("\\.");
        this.extension = chunks[chunks.length - 1].toLowerCase();
    }

    public static Filename fromString(final String str) {
        return new Filename(str);
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filename filename = (Filename) o;
        return Objects.equals(value, filename.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}