package be.koder.library.api.presenter;

import java.net.URL;

public interface UploadHardcoverPresenter {

    void uploaded(URL hardcover);

    void fileExtensionNotAllowed();

    void fileEmpty();

    void fileTooLarge();

    void bookNotFound();

    void uploadFailed(String reason);
}