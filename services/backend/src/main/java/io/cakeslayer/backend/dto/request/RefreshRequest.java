package io.cakeslayer.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(
        title = "RefreshRequest",
        description = "Request object for token refresh. Provides a valid refresh token to obtain a new access token.",
        example = "{\"refresh_token\": \"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...\"}"
)
public record RefreshRequest(
        @Schema(
                description = "Valid refresh token from previous authentication. This token must not be expired or revoked.",
                example = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huZG9lIiwiaWF0IjoxNzA0Njc2ODAwLCJleHAiOjE3MDcyNjg4MDB9.signature",
                type = "string",
                title = "Refresh Token"
        )
        @NotBlank(message = "Refresh token is required")
        @JsonProperty("refresh_token")
        String refreshToken
) {
}
