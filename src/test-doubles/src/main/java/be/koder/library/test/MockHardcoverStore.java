package be.koder.library.test;

import be.koder.library.domain.book.hardcover.HardcoverStore;
import be.koder.library.domain.book.hardcover.HardcoverStoreResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class MockHardcoverStore implements HardcoverStore {

    private boolean fail;
    private final Map<String, byte[]> data = new HashMap<>();

    public void enableFail() {
        fail = true;
    }

    @Override
    public HardcoverStoreResponse store(final String key, final byte[] value) {
        if (fail) {
            fail = false;
            return HardcoverStoreResponse.failed("Storage failed");
        }
        try {
            data.put(key, value);
            final var url = new URL(String.format("https://www.mock.com/images/%s", key));
            return HardcoverStoreResponse.success(url);
        } catch (final MalformedURLException e) {
            return HardcoverStoreResponse.failed("Storage failed");
        }
    }
}