package be.koder.library.rest.modifybook;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record BookModifiedResponse(@Schema(requiredMode = Schema.RequiredMode.REQUIRED) UUID bookId) {
}