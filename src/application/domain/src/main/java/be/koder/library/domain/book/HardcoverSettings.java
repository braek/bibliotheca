package be.koder.library.domain.book;

import java.util.List;

public interface HardcoverSettings {

    // Allowed image types (in lowercase)
    List<String> ALLOWED_EXTENSIONS = List.of(
            "gif",
            "jpe",
            "jpeg",
            "jpg",
            "png"
    );

    // Maximum file size in bytes
    int MAX_FILE_SIZE = 2 * 1000 * 1000;
}