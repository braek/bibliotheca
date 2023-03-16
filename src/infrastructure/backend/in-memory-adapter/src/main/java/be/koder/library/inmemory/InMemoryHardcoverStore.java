package be.koder.library.inmemory;

import be.koder.library.domain.book.HardcoverStore;
import be.koder.library.domain.book.HardcoverStoreResponse;

import java.util.HashMap;
import java.util.Map;

public final class InMemoryHardcoverStore implements HardcoverStore {

    private final Map<String, byte[]> store = new HashMap<>();

    @Override
    public HardcoverStoreResponse store(String key, byte[] value) {
        store.put(key, value);
        return HardcoverStoreResponse.success(null);
    }
}