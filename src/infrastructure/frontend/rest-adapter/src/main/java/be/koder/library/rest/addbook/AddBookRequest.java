package be.koder.library.rest.addbook;

import io.swagger.v3.oas.annotations.media.Schema;

public record AddBookRequest(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String isbn,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String title,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String author
) {
}