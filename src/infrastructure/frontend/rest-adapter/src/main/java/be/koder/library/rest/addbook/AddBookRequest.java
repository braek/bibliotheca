package be.koder.library.rest.addbook;

public record AddBookRequest(String isbn, String title, String author) {
}