package be.koder.library.vocabulary.file;

public final class InvalidFilenameException extends RuntimeException {
    public InvalidFilenameException(String value) {
        super(String.format("Cannot create Filename from string %s", value));
    }
}