package be.koder.library.rest.modifybook;

import io.swagger.v3.oas.annotations.media.Schema;

public record ModifyBookRequest(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, minLength = 10, maxLength = 13) String isbn,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 100) String title,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 100) String author
) {
}