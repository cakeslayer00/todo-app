package io.cakeslayer.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        title = "AuthResponse",
        description = "Response object containing authentication tokens and user information. Returned on successful login, registration, or token refresh.",
        example = "{\"username\": \"johndoe\", \"access_token\": \"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...\", \"refresh_token\": \"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...\"}"
)
public record AuthResponse(
        @Schema(
                description = "Username of the authenticated user",
                example = "johndoe",
                type = "string",
                title = "Username"
        )
        String username,

        @Schema(
                description = "JWT access token for API requests. Valid for 15 minutes (900,000ms). Include in Authorization header as Bearer token.",
                example = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huZG9lIiwiaWF0IjoxNzA0Njc2ODAwLCJleHAiOjE3MDQ2Nzc3MDB9.signature",
                type = "string",
                title = "Access Token",
                format = "bearer"
        )
        @JsonProperty("access_token")
        String accessToken,

        @Schema(
                description = "JWT refresh token for obtaining new access tokens. Valid for 30 days (2,592,000 seconds). Use this token to get a new access token when the current one expires.",
                example = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huZG9lIiwiaWF0IjoxNzA0Njc2ODAwLCJleHAiOjE3MDcyNjg4MDB9.signature",
                type = "string",
                title = "Refresh Token",
                format = "bearer"
        )
        @JsonProperty("refresh_token")
        String refreshToken
) {
}
