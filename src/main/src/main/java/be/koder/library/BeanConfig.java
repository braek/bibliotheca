package be.koder.library;

import be.koder.library.api.AddBook;
import be.koder.library.api.ModifyBook;
import be.koder.library.api.RemoveBook;
import be.koder.library.api.SetHardcover;
import be.koder.library.domain.book.BookRepository;
import be.koder.library.domain.book.HardcoverStore;
import be.koder.library.domain.book.IsbnService;
import be.koder.library.domain.event.EventPublisher;
import be.koder.library.inmemory.InMemoryBookRepository;
import be.koder.library.inmemory.InMemoryEventPublisher;
import be.koder.library.inmemory.InMemoryHardcoverStore;
import be.koder.library.usecase.addbook.AddBookUseCase;
import be.koder.library.usecase.modifybook.ModifyBookUseCase;
import be.koder.library.usecase.removebook.RemoveBookUseCase;
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
    AddBook addBook(BookRepository bookRepository, IsbnService isbnService, EventPublisher eventPublisher) {
        return new AddBookUseCase(bookRepository, isbnService, eventPublisher);
    }

    @Bean
    ModifyBook modifyBook(BookRepository bookRepository, EventPublisher eventPublisher) {
        return new ModifyBookUseCase(bookRepository, eventPublisher);
    }

    @Bean
    HardcoverStore hardcoverStore() {
        return new InMemoryHardcoverStore();
    }

    @Bean
    RemoveBook removeBook(BookRepository bookRepository, EventPublisher eventPublisher) {
        return new RemoveBookUseCase(bookRepository, eventPublisher);
    }

    @Bean
    SetHardcover setHardcover(BookRepository bookRepository, HardcoverStore hardcoverStore, EventPublisher eventPublisher) {
        return new SetHardcoverUseCase(bookRepository, hardcoverStore, eventPublisher);
    }
}