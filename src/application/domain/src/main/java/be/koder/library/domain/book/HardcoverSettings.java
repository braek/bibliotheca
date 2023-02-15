package be.koder.library.domain.book;

import be.koder.library.vocabulary.file.Extension;

import java.util.List;

public interface HardcoverSettings {

    // Allowed image types (in lowercase)
    List<Extension> ALLOWED_EXTENSIONS = List.of(
            Extension.fromString("gif"),
            Extension.fromString("jpe"),
            Extension.fromString("jpeg"),
            Extension.fromString("jpg"),
            Extension.fromString("png")
    );

    // Maximum file size in bytes
    int MAX_FILE_SIZE = 2 * 1000 * 1000;
}