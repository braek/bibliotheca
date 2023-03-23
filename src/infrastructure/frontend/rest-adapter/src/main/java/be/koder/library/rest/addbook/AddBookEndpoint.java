package be.koder.library.rest.addbook;

import be.koder.library.rest.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public interface AddBookEndpoint {
    @Operation(
            requestBody = @RequestBody(
                    content = @Content(
                            schema = @Schema(
                                    implementation = AddBookRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            description = "Book is added",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = BookAddedResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "Fout in request body",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ErrorResponse.class
                                    )
                            )
                    )
            }
    )
    @PostMapping("/books")
    ResponseEntity<Object> addBook(AddBookRequest request);
}