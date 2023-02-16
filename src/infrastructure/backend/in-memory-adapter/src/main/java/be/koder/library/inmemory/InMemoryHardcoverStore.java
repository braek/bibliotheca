package be.koder.library.inmemory;

import be.koder.library.domain.book.HardcoverStore;
import be.koder.library.domain.book.HardcoverStoreResponse;

public final class InMemoryHardcoverStore implements HardcoverStore {
    @Override
    public HardcoverStoreResponse store(String key, byte[] value) {
        return null;
    }
}