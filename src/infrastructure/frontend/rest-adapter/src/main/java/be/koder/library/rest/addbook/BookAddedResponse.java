package be.koder.library.rest.addbook;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record BookAddedResponse(@Schema(requiredMode = Schema.RequiredMode.REQUIRED) UUID bookId) {
}