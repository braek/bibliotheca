package be.koder.library.swing;

import be.koder.library.api.*;

public record Facade(
        ListBooks listBooks,
        AddBook addBook,
        ModifyBook modifyBook,
        RemoveBook removeBook,
        UploadHardcover uploadHardcover
) {
}