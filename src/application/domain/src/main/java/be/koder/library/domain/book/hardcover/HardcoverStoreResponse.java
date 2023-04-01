package be.koder.library.domain.book.hardcover;

import java.net.URL;

public final class HardcoverStoreResponse {

    private final URL url;
    private final boolean nok;
    private final String error;

    private HardcoverStoreResponse(final URL url, final boolean nok, final String error) {
        this.url = url;
        this.nok = nok;
        this.error = error;
    }

    public boolean isNok() {
        return nok;
    }

    public URL getUrl() {
        return url;
    }

    public String getError() {
        return error;
    }

    public static HardcoverStoreResponse success(final URL url) {
        return new HardcoverStoreResponse(url, false, null);
    }

    public static HardcoverStoreResponse failed(final String error) {
        return new HardcoverStoreResponse(null, true, error);
    }
}