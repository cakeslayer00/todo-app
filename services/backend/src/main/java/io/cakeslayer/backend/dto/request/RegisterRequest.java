package io.cakeslayer.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
        title = "RegisterRequest",
        description = "Request object for user registration. All fields are required and must pass validation constraints.",
        example = "{\"username\": \"johndoe\", \"email\": \"john@example.com\", \"password\": \"SecurePassword123\"}"
)
public record RegisterRequest(
        @Schema(
                description = "Username for the account",
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
                description = "Email address for the account. Must be a valid email format.",
                example = "john@example.com",
                type = "string",
                title = "Email",
                format = "email"
        )
        @Email(message = "Email should be valid")
        @NotBlank(message = "Email is required")
        String email,

        @Schema(
                description = "Password for the account. Minimum 6 characters required.",
                example = "SecurePassword123",
                minLength = 6,
                type = "string",
                title = "Password",
                pattern = "^.{6,}$"
        )
        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password should be at least 6 characters")
        String password
) {
}
