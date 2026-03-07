package io.cakeslayer.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
        title = "LoginRequest",
        description = "Request object for user login authentication. Both username and password are required.",
        example = "{\"username\": \"johndoe\", \"password\": \"SecurePassword123\"}"
)
public record LoginRequest(
        @Schema(
                description = "Username for login. Minimum 6 characters required.",
                example = "johndoe",
                minLength = 6,
                type = "string",
                title = "Username",
                pattern = "^.{6,}$"
        )
        @NotBlank(message = "Username is required")
        @Size(min = 6, message = "Username should be at least 6 characters")
        String username,

        @Schema(
                description = "User password for authentication.",
                example = "SecurePassword123",
                type = "string",
                title = "Password"
        )
        @NotBlank(message = "Password is required")
        String password
) {
}
