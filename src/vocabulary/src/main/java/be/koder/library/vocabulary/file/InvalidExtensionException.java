package be.koder.library.vocabulary.file;

public final class InvalidExtensionException extends RuntimeException {
    public InvalidExtensionException(String value) {
        super(String.format("Cannot create Extension from string %s", value));
    }
}