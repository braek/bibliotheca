package be.koder.library.rest.removehardcover;

import be.koder.library.rest.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface RemoveHardcoverEndpoint {
    @Operation(
            tags = "Books",
            responses = {
                    @ApiResponse(
                            description = "Hardcover is removed",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = HardcoverRemovedResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "Boek heeft geen hardcover",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ErrorResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "Boek bestaat niet",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ErrorResponse.class
                                    )
                            )
                    )
            }
    )
    @DeleteMapping(
            value = "/books/{bookId}/hardcover"
    )
    ResponseEntity<Object> removeHardcover(@PathVariable UUID bookId);
}