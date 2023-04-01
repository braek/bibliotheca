package be.koder.library;

import be.koder.library.api.*;
import be.koder.library.domain.book.BookRepository;
import be.koder.library.domain.book.hardcover.HardcoverStore;
import be.koder.library.domain.book.isbn.IsbnService;
import be.koder.library.domain.event.EventPublisher;
import be.koder.library.inmemory.InMemoryBookRepository;
import be.koder.library.inmemory.InMemoryEventPublisher;
import be.koder.library.inmemory.InMemoryHardcoverStore;
import be.koder.library.query.book.BookArchive;
import be.koder.library.query.book.ListBooksQuery;
import be.koder.library.usecase.addbook.AddBookUseCase;
import be.koder.library.usecase.modifybook.ModifyBookUseCase;
import be.koder.library.usecase.removebook.RemoveBookUseCase;
import be.koder.library.usecase.removehardcover.RemoveHardcoverUseCase;
import be.koder.library.usecase.sethardcover.SetHardcoverUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    EventPublisher eventPublisher() {
        return new InMemoryEventPublisher();
    }

    @Bean
    InMemoryBookRepository bookRepository() {
        return new InMemoryBookRepository();
    }

    @Bean
    HardcoverStore hardcoverStore() {
        return new InMemoryHardcoverStore();
    }

    @Bean
    ListBooks listBooks(BookArchive bookArchive) {
        return new ListBooksQuery(bookArchive);
    }

    @Bean
    AddBook addBook(BookRepository bookRepository, IsbnService isbnService, EventPublisher eventPublisher) {
        return new AddBookUseCase(bookRepository, isbnService, eventPublisher);
    }

    @Bean
    ModifyBook modifyBook(BookRepository bookRepository, EventPublisher eventPublisher) {
        return new ModifyBookUseCase(bookRepository, eventPublisher);
    }

    @Bean
    SetHardcover setHardcover(BookRepository bookRepository, HardcoverStore hardcoverStore, EventPublisher eventPublisher) {
        return new SetHardcoverUseCase(bookRepository, hardcoverStore, eventPublisher);
    }

    @Bean
    RemoveBook removeBook(BookRepository bookRepository, EventPublisher eventPublisher) {
        return new RemoveBookUseCase(bookRepository, eventPublisher);
    }

    @Bean
    RemoveHardcover removeHardcover(BookRepository bookRepository, EventPublisher eventPublisher) {
        return new RemoveHardcoverUseCase(bookRepository, eventPublisher);
    }
}