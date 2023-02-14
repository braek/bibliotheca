package be.koder.library.test;

import be.koder.library.domain.book.HardcoverStore;
import be.koder.library.domain.book.HardcoverStoreResponse;

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
            return HardcoverStoreResponse.failed("Store failed");
        }
        try {
            data.put(key, value);
            final var url = new URL(String.format("https://www.mock.com/images/%s", key));
            return HardcoverStoreResponse.okay(url);
        } catch (final MalformedURLException e) {
            return HardcoverStoreResponse.failed("Store failed");
        }
    }
}