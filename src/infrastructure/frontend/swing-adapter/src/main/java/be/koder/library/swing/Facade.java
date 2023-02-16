package be.koder.library.swing;

import be.koder.library.api.AddBook;
import be.koder.library.api.ModifyBook;
import be.koder.library.api.RemoveBook;
import be.koder.library.api.UploadHardcover;

public record Facade(
        AddBook addBook,
        ModifyBook modifyBook,
        RemoveBook removeBook,
        UploadHardcover uploadHardcover
) {
}