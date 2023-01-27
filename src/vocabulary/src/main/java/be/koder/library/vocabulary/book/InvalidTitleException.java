package be.koder.library.vocabulary.book;

public final class InvalidTitleException extends RuntimeException {
    public InvalidTitleException(String value) {
        super(String.format("Cannot create Title from string %s", value));
    }
}