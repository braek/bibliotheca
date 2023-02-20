package be.koder.library.api.presenter;

import java.net.URL;

public interface SetHardcoverPresenter {

    void set(URL hardcover);

    void fileExtensionNotAllowed();

    void fileEmpty();

    void fileTooLarge();

    void bookNotFound();

    void storageFailed(String reason);
}