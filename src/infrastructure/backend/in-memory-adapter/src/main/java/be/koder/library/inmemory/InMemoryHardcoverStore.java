package be.koder.library.inmemory;

import be.koder.library.domain.book.hardcover.HardcoverStore;
import be.koder.library.domain.book.hardcover.HardcoverStoreResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class InMemoryHardcoverStore implements HardcoverStore {

    private final Map<String, byte[]> store = new HashMap<>();

    @Override
    public HardcoverStoreResponse store(String key, byte[] value) {
        store.put(key, value);
        try {
            return HardcoverStoreResponse.success(new URL(String.format("https://in.memory.impl/images/%s", key)));
        } catch (MalformedURLException e) {
            return HardcoverStoreResponse.failed("URL generation failed");
        }
    }
}