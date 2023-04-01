package be.koder.library.domain.book.hardcover;

// Domain service that represents an image store (for book hardcovers in this case).
// Can be implemented against Amazon S3 for example.
public interface HardcoverStore {
    HardcoverStoreResponse store(String key, byte[] value);
}