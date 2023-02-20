package be.koder.library.usecase.book;

import be.koder.library.api.SetHardcover;
import be.koder.library.api.presenter.SetHardcoverPresenter;
import be.koder.library.domain.book.Book;
import be.koder.library.domain.book.BookSnapshot;
import be.koder.library.domain.book.HardcoverSettings;
import be.koder.library.domain.book.event.HardcoverSet;
import be.koder.library.test.BookObjectMother;
import be.koder.library.test.MockBookRepository;
import be.koder.library.test.MockEventPublisher;
import be.koder.library.test.MockHardcoverStore;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.file.Filename;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URL;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Given an API to set hardcovers for Books")
class SetHardcoverTest {

    private final MockBookRepository bookRepository = new MockBookRepository();
    private final MockHardcoverStore hardcoverStore = new MockHardcoverStore();
    private final MockEventPublisher eventPublisher = new MockEventPublisher();
    private final SetHardcover setHardcover = new SetHardcoverUseCase(bookRepository, hardcoverStore, eventPublisher);

    @Nested
    @DisplayName("when hardcover was set successfully")
    class TestHappyFlow implements SetHardcoverPresenter {

        private final BookSnapshot book = BookObjectMother.INSTANCE.cleanCode;
        private final Filename filename = Filename.fromString("HardcoverForBook.jpg");
        private final byte[] data = "TheImage".getBytes();
        private BookSnapshot updatedBook;
        private boolean setCalled;

        @BeforeEach
        void setup() {
            bookRepository.save(Book.fromSnapshot(book));
            setHardcover.setHardcover(book.id(), filename, data, this);
            updatedBook = bookRepository.get(book.id()).map(Book::takeSnapshot).orElseThrow();
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(setCalled);
        }

        @Test
        @DisplayName("it should be added to the Book")
        void addedToBook() {
            assertThat(updatedBook.hardcover()).isNotNull();
            assertThat(updatedBook.hardcover().toString()).endsWith(String.format("%s.%s", book.id(), filename.getExtension()));
        }

        @Test
        @DisplayName("it should publish an event")
        void eventPublished() {
            assertThat(eventPublisher.getLastEvent()).hasValueSatisfying(it -> {
                assertThat(it).isInstanceOf(HardcoverSet.class);
                var event = (HardcoverSet) it;
                assertThat(event.hardcover().toString()).endsWith(String.format("%s.%s", book.id(), filename.getExtension()));
            });
        }

        @Override
        public void set(URL hardcover) {
            setCalled = true;
        }

        @Override
        public void fileExtensionNotAllowed() {
            fail();
        }

        @Override
        public void fileEmpty() {
            fail();
        }

        @Override
        public void fileTooLarge() {
            fail();
        }

        @Override
        public void bookNotFound() {
            fail();
        }

        @Override
        public void storageFailed(String reason) {
            fail();
        }
    }

    @Nested
    @DisplayName("when file extension not allowed")
    class TestWhenFileExtensionNotAllowed implements SetHardcoverPresenter {

        private final BookSnapshot book = BookObjectMother.INSTANCE.cleanCode;
        private final byte[] data = "TheImage".getBytes();
        private boolean fileExtensionNotAllowedCalled;

        private static Stream<? extends Arguments> params() {
            return Stream.of(
                    Arguments.of("pdf"),
                    Arguments.of("html"),
                    Arguments.of("tiff"),
                    Arguments.of("docx"),
                    Arguments.of("doc"),
                    Arguments.of("xml"),
                    Arguments.of("txt"),
                    Arguments.of("java"),
                    Arguments.of("json"),
                    Arguments.of("sql")
            );
        }

        @BeforeEach
        void setup() {
            bookRepository.save(Book.fromSnapshot(book));
        }

        @ParameterizedTest
        @MethodSource("params")
        @DisplayName("it should provide feedback")
        void feedbackProvided(final String extension) {
            final Filename filename = Filename.fromString(String.format("HardcoverForBook.%s", extension));
            setHardcover.setHardcover(book.id(), filename, data, this);
            assertThat(fileExtensionNotAllowedCalled).isTrue();
        }

        @Override
        public void fileExtensionNotAllowed() {
            fileExtensionNotAllowedCalled = true;
        }

        @Override
        public void set(URL hardcover) {
            fail();
        }

        @Override
        public void fileEmpty() {
            fail();
        }

        @Override
        public void fileTooLarge() {
            fail();
        }

        @Override
        public void bookNotFound() {
            fail();
        }

        @Override
        public void storageFailed(String reason) {
            fail();
        }
    }

    @Nested
    @DisplayName("when hardcover set for non-existing Book")
    class TestWhenBookNotFound implements SetHardcoverPresenter {

        private boolean bookNotFoundCalled;

        @BeforeEach
        void setup() {
            setHardcover.setHardcover(BookId.createNew(), Filename.fromString("TheHardcover.jpg"), "ImageBytes".getBytes(), this);
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertThat(bookNotFoundCalled).isTrue();
        }

        @Override
        public void set(URL hardcover) {
            fail();
        }

        @Override
        public void fileExtensionNotAllowed() {
            fail();
        }

        @Override
        public void fileEmpty() {
            fail();
        }

        @Override
        public void fileTooLarge() {
            fail();
        }

        @Override
        public void bookNotFound() {
            bookNotFoundCalled = true;
        }

        @Override
        public void storageFailed(String reason) {
            fail();
        }
    }

    @Nested
    @DisplayName("when hardcover storage fails")
    class TestWhenStorageFails implements SetHardcoverPresenter {

        private final BookSnapshot book = BookObjectMother.INSTANCE.cleanCode;
        private final Filename filename = Filename.fromString("HardcoverForBook.jpg");
        private final byte[] data = "TheImage".getBytes();
        private boolean storageFailedCalled;

        @BeforeEach
        void setup() {
            bookRepository.save(Book.fromSnapshot(book));
            hardcoverStore.enableFail();
            setHardcover.setHardcover(book.id(), filename, data, this);
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertThat(storageFailedCalled).isTrue();
        }

        @Override
        public void storageFailed(String reason) {
            storageFailedCalled = true;
        }

        @Override
        public void set(URL hardcover) {
            fail();
        }

        @Override
        public void fileExtensionNotAllowed() {
            fail();
        }

        @Override
        public void fileEmpty() {
            fail();
        }

        @Override
        public void fileTooLarge() {
            fail();
        }

        @Override
        public void bookNotFound() {
            fail();
        }
    }

    @Nested
    @DisplayName("when hardcover set that is empty")
    class TestWhenHardcoverEmpty implements SetHardcoverPresenter {

        private final BookSnapshot book = BookObjectMother.INSTANCE.cleanCode;
        private final Filename filename = Filename.fromString("HardcoverForBook.jpg");
        private final byte[] data = "".getBytes();
        private boolean fileEmptyCalled;

        @BeforeEach
        void setup() {
            bookRepository.save(Book.fromSnapshot(book));
            setHardcover.setHardcover(book.id(), filename, data, this);
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertThat(fileEmptyCalled).isTrue();
        }

        @Override
        public void fileEmpty() {
            fileEmptyCalled = true;
        }

        @Override
        public void set(URL hardcover) {
            fail();
        }

        @Override
        public void fileExtensionNotAllowed() {
            fail();
        }

        @Override
        public void fileTooLarge() {
            fail();
        }

        @Override
        public void bookNotFound() {
            fail();
        }

        @Override
        public void storageFailed(String reason) {
            fail();
        }
    }

    @Nested
    @DisplayName("when hardcover set that is too large")
    class TestWhenHardcoverTooLarge implements SetHardcoverPresenter {

        private final BookSnapshot book = BookObjectMother.INSTANCE.cleanCode;
        private final Filename filename = Filename.fromString("HardcoverForBook.jpg");
        private boolean fileTooLargeCalled;

        @BeforeEach
        void setup() {
            bookRepository.save(Book.fromSnapshot(book));
            byte[] data = new byte[HardcoverSettings.MAX_FILE_SIZE + 1];
            new Random().nextBytes(data);
            setHardcover.setHardcover(book.id(), filename, data, this);
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertThat(fileTooLargeCalled).isTrue();
        }

        @Override
        public void fileTooLarge() {
            fileTooLargeCalled = true;
        }

        @Override
        public void set(URL hardcover) {
            fail();
        }

        @Override
        public void fileExtensionNotAllowed() {
            fail();
        }

        @Override
        public void fileEmpty() {
            fail();
        }

        @Override
        public void bookNotFound() {
            fail();
        }

        @Override
        public void storageFailed(String reason) {
            fail();
        }
    }
}