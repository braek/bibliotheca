package be.koder.library.vocabulary.book;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IsbnTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "1234567890",
            "1234567890123"
    })
    void testValidIsbn(final String str) {
        var isbn = Isbn.fromString(str);
        assertThat(isbn).isNotNull();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            "ABC",
            "123456789",
            "12345678901234"
    })
    void testInvalidIsbn(final String str) {
        assertThrows(InvalidIsbnException.class, () -> Isbn.fromString(str));
    }
}