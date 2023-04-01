package be.koder.library.usecase.sethardcover;

import be.koder.library.api.SetHardcover;
import be.koder.library.api.presenter.SetHardcoverPresenter;
import be.koder.library.domain.book.BookRepository;
import be.koder.library.domain.book.hardcover.HardcoverStore;
import be.koder.library.domain.event.EventPublisher;
import be.koder.library.usecase.BufferedEventPublisher;
import be.koder.library.usecase.UseCase;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.file.Filename;

import static java.util.Objects.requireNonNull;

public final class SetHardcoverUseCase implements UseCase<SetHardcoverCommand, SetHardcoverPresenter>, SetHardcover {

    private final BookRepository bookRepository;
    private final HardcoverStore hardcoverStore;
    private final EventPublisher eventPublisher;

    public SetHardcoverUseCase(final BookRepository bookRepository, final HardcoverStore hardcoverStore, final EventPublisher eventPublisher) {
        this.bookRepository = bookRepository;
        this.hardcoverStore = hardcoverStore;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void setHardcover(final BookId bookId, final Filename filename, final byte[] data, final SetHardcoverPresenter presenter) {
        requireNonNull(bookId, "BookId cannot be NULL");
        requireNonNull(filename, "Filename cannot be NULL");
        requireNonNull(data, "Data cannot be NULL");
        execute(new SetHardcoverCommand(bookId, filename, data), presenter);
    }

    @Override
    public void execute(final SetHardcoverCommand command, final SetHardcoverPresenter presenter) {
        new BufferedEventPublisher(eventPublisher).doBuffered(it -> executeBuffered(command, presenter, it));
    }

    private void executeBuffered(final SetHardcoverCommand command, final SetHardcoverPresenter presenter, final EventPublisher theEventPublisher) {
        bookRepository.get(command.bookId()).ifPresentOrElse(book -> {
            book.setHardcover(
                    command.filename(),
                    command.data(),
                    hardcoverStore,
                    new SetHardcoverEventPublisherDecorator(presenter, theEventPublisher)
            );
            bookRepository.save(book);
        }, presenter::bookNotFound);
    }
}