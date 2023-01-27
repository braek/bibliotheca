package be.koder.library.vocabulary.book;

public final class InvalidAuthorException extends RuntimeException {
    public InvalidAuthorException(String value) {
        super(String.format("Cannot create Author from string %s", value));
    }
}