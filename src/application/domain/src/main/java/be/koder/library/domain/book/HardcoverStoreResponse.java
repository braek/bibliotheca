package be.koder.library.domain.book;

import java.net.URL;

public final class HardcoverStoreResponse {

    private final URL url;
    private final boolean okay;
    private final String error;

    private HardcoverStoreResponse(final URL url, final boolean okay, final String error) {
        this.url = url;
        this.okay = okay;
        this.error = error;
    }

    public boolean isOkay() {
        return okay;
    }

    public URL getUrl() {
        return url;
    }

    public String getError() {
        return error;
    }

    public static HardcoverStoreResponse okay(final URL url) {
        return new HardcoverStoreResponse(url, true, null);
    }

    public static HardcoverStoreResponse failed(final String error) {
        return new HardcoverStoreResponse(null, false, error);
    }
}