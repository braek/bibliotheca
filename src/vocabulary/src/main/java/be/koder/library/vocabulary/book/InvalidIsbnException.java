package be.koder.library.vocabulary.book;

public final class InvalidIsbnException extends RuntimeException {
    public InvalidIsbnException(String value) {
        super(String.format("Cannot create ISBN from string %s", value));
    }
}