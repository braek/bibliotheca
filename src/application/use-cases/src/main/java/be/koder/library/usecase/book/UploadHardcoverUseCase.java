package be.koder.library.usecase.book;

import be.koder.library.api.UploadHardcover;
import be.koder.library.api.presenter.UploadHardcoverPresenter;
import be.koder.library.domain.book.BookRepository;
import be.koder.library.domain.book.HardcoverStore;
import be.koder.library.domain.event.EventPublisher;
import be.koder.library.usecase.BufferedEventPublisher;
import be.koder.library.usecase.UseCase;
import be.koder.library.usecase.book.command.UploadBookCoverCommand;
import be.koder.library.usecase.book.event.UploadHardcoverEventPublisherDecorator;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.file.Filename;

public final class UploadHardcoverUseCase implements UseCase<UploadBookCoverCommand, UploadHardcoverPresenter>, UploadHardcover {

    private final BookRepository bookRepository;
    private final HardcoverStore hardcoverStore;
    private final EventPublisher eventPublisher;

    public UploadHardcoverUseCase(final BookRepository bookRepository, final HardcoverStore hardcoverStore, final EventPublisher eventPublisher) {
        this.bookRepository = bookRepository;
        this.hardcoverStore = hardcoverStore;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void uploadHardcover(final BookId bookId, final Filename filename, final byte[] data, final UploadHardcoverPresenter presenter) {
        execute(new UploadBookCoverCommand(bookId, filename, data), presenter);
    }

    @Override
    public void execute(final UploadBookCoverCommand command, final UploadHardcoverPresenter presenter) {
        new BufferedEventPublisher(eventPublisher).doBuffered(it -> executeBuffered(command, presenter, it));
    }

    private void executeBuffered(final UploadBookCoverCommand command, final UploadHardcoverPresenter presenter, final EventPublisher theEventPublisher) {
        bookRepository.get(command.bookId()).ifPresentOrElse(book -> {
            book.uploadHardcover(
                    command.filename(),
                    command.data(),
                    hardcoverStore,
                    new UploadHardcoverEventPublisherDecorator(presenter, theEventPublisher)
            );
            bookRepository.save(book);
        }, presenter::bookNotFound);
    }
}