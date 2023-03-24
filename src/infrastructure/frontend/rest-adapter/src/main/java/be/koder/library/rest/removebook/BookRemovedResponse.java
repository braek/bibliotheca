package be.koder.library.rest.removebook;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record BookRemovedResponse(@Schema(requiredMode = Schema.RequiredMode.REQUIRED) UUID bookId) {
}