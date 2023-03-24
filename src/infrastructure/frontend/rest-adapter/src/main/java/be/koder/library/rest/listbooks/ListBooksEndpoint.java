package be.koder.library.rest.listbooks;

import be.koder.library.vocabulary.dto.BookListItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface ListBooksEndpoint {

    @Operation(
            tags = {
                    "Books"
            },
            responses = {
                    @ApiResponse(
                            description = "List of Books",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = ListBooksItem.class
                                            )
                                    )
                            )
                    )
            }
    )
    @GetMapping(
            value = "/books",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<ListBooksItem>> listBooks();
}