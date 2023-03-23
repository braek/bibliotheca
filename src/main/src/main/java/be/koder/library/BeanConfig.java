package be.koder.library;

import be.koder.library.api.AddBook;
import be.koder.library.domain.book.BookRepository;
import be.koder.library.domain.book.IsbnService;
import be.koder.library.domain.event.EventPublisher;
import be.koder.library.inmemory.InMemoryBookRepository;
import be.koder.library.inmemory.InMemoryEventPublisher;
import be.koder.library.usecase.addbook.AddBookUseCase;
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
}