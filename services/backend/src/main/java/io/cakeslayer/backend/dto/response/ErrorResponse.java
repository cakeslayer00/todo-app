package io.cakeslayer.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(
        title = "ErrorResponse",
        description = "Standard error response object returned when requests fail validation or encounter errors.",
        example = "{\"status\": 400, \"message\": \"Validation failed\", \"errors\": {\"username\": \"Username should be at least 6 characters\", \"email\": \"Email should be valid\"}}"
)
public record ErrorResponse(
        @Schema(
                description = "HTTP status code",
                example = "400",
                type = "integer",
                title = "Status Code"
        )
        int status,

        @Schema(
                description = "Error message or description",
                example = "Validation failed",
                type = "string",
                title = "Message"
        )
        String message,

        @Schema(
                description = "Map of field-level validation errors. Only present when status is 400 (Bad Request). Each key is the field name and value is the validation error message.",
                example = "{\"username\": \"Username should be at least 6 characters\", \"email\": \"Email should be valid\", \"password\": \"Password should be at least 6 characters\"}",
                type = "object",
                title = "Validation Errors"
        )
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Map<String, String> errors
) {
    public ErrorResponse(int status, String message) {
        this(status, message, null);
    }
}
