package be.koder.library.domain.book;

public interface HardcoverStore {
    HardcoverStoreResponse store(String key, byte[] value);
}