package be.koder.library.main;

import be.koder.library.api.*;
import be.koder.library.inmemory.InMemoryBookRepository;
import be.koder.library.inmemory.InMemoryEventPublisher;
import be.koder.library.inmemory.InMemoryHardcoverStore;
import be.koder.library.query.book.ListBooksQuery;
import be.koder.library.usecase.book.AddBookUseCase;
import be.koder.library.usecase.book.ModifyBookUseCase;
import be.koder.library.usecase.book.RemoveBookUseCase;
import be.koder.library.usecase.book.UploadHardcoverUseCase;

public final class AppContext {

    // Internal kitchen: hidden from outside world
    private static final InMemoryBookRepository bookRepository = new InMemoryBookRepository();
    private static final InMemoryEventPublisher eventPublisher = new InMemoryEventPublisher();
    private static final InMemoryHardcoverStore hardcoverStore = new InMemoryHardcoverStore();

    // Public API: to be consumed by frontend adapters
    public static final ListBooks listBooks = new ListBooksQuery(bookRepository);
    public static final AddBook addBook = new AddBookUseCase(bookRepository, bookRepository, eventPublisher);
    public static final ModifyBook modifyBook = new ModifyBookUseCase(bookRepository, eventPublisher);
    public static final RemoveBook removeBook = new RemoveBookUseCase(bookRepository, eventPublisher);
    public static final UploadHardcover uploadHardcover = new UploadHardcoverUseCase(bookRepository, hardcoverStore, eventPublisher);
}