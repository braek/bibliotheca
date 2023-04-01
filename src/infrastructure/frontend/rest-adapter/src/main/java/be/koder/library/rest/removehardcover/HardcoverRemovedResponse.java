package be.koder.library.rest.removehardcover;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record HardcoverRemovedResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) UUID bookId
) {
}