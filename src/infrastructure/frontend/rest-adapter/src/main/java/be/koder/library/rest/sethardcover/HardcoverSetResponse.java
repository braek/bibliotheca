package be.koder.library.rest.sethardcover;

import io.swagger.v3.oas.annotations.media.Schema;

import java.net.URL;
import java.util.UUID;

public record HardcoverSetResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) UUID bookId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) URL hardcover
) {
}