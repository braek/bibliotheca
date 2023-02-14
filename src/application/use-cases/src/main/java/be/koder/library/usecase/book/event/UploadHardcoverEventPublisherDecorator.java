package be.koder.library.usecase.book.event;

import be.koder.library.api.presenter.UploadHardcoverPresenter;
import be.koder.library.domain.book.event.HardcoverUploaded;
import be.koder.library.domain.book.event.UploadHardcoverEventPublisher;
import be.koder.library.domain.event.EventPublisher;
import be.koder.library.vocabulary.book.BookId;

import java.net.URL;

public final class UploadHardcoverEventPublisherDecorator implements UploadHardcoverEventPublisher {

    private final UploadHardcoverPresenter presenter;
    private final EventPublisher eventPublisher;

    public UploadHardcoverEventPublisherDecorator(UploadHardcoverPresenter presenter, EventPublisher eventPublisher) {
        this.presenter = presenter;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void uploaded(BookId bookId, URL hardcover) {
        eventPublisher.publish(new HardcoverUploaded(bookId, hardcover));
        presenter.uploaded(hardcover);
    }

    @Override
    public void uploadFailed(String reason) {
        presenter.uploadFailed(reason);
    }

    @Override
    public void fileExtensionNotAllowed() {
        presenter.fileExtensionNotAllowed();
    }

    @Override
    public void fileEmpty() {
        presenter.fileEmpty();
    }

    @Override
    public void fileTooLarge() {
        presenter.fileTooLarge();
    }
}
