package be.koder.library.rest;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorResponse(@Schema(requiredMode = Schema.RequiredMode.REQUIRED) String message) {
}