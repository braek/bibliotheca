package be.koder.library.usecase.book;

import be.koder.library.api.SetHardcover;
import be.koder.library.api.presenter.SetHardcoverPresenter;
import be.koder.library.domain.book.BookRepository;
import be.koder.library.domain.book.HardcoverStore;
import be.koder.library.domain.event.EventPublisher;
import be.koder.library.usecase.BufferedEventPublisher;
import be.koder.library.usecase.UseCase;
import be.koder.library.usecase.book.command.SetHardcoverCommand;
import be.koder.library.usecase.book.event.SetHardcoverEventPublisherDecorator;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.file.Filename;

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