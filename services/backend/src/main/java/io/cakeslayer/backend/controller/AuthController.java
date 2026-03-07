package io.cakeslayer.backend.controller;

import io.cakeslayer.backend.dto.request.LoginRequest;
import io.cakeslayer.backend.dto.request.RefreshRequest;
import io.cakeslayer.backend.dto.request.RegisterRequest;
import io.cakeslayer.backend.dto.response.AuthResponse;
import io.cakeslayer.backend.security.authentication.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and token management")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    @Operation(summary = "Register a new user", description = "Creates a new user account with the provided credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = "*/*", examples = @ExampleObject(value = """
                            {
                                "status": 400,
                                "message": "Validation failed",
                                "errors": {
                                    "username": "Username should be at least 6 characters",
                                    "email": "Email should be valid"
                                }
                            }
                            """))),
            @ApiResponse(responseCode = "409", description = "Username or email already exists",
                    content = @Content(mediaType = "*/*", examples = @ExampleObject(value = """
                            {
                                "status": 409,
                                "message": "Username or email already exists"
                            }
                            """)))
    })
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticates a user with username and password, returning access and refresh tokens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = "*/*", examples = @ExampleObject(value = """
                            {
                                "status": 400,
                                "message": "Validation failed",
                                "errors": {
                                    "username": "Username is required",
                                    "password": "Password is required"
                                }
                            }
                            """))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content(mediaType = "*/*", examples = @ExampleObject(value = """
                            {
                                "status": 401,
                                "message": "Invalid credentials"
                            }
                            """)))
    })
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token", description = "Exchanges a valid refresh token for a new access token. Implements refresh token rotation for enhanced security")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = "*/*", examples = @ExampleObject(value = """
                            {
                                "status": 400,
                                "message": "Validation failed",
                                "errors": {
                                    "refresh_token": "Refresh token is required"
                                }
                            }
                            """))),
            @ApiResponse(responseCode = "401", description = "Token not found, expired, or revoked",
                    content = @Content(mediaType = "*/*", examples = @ExampleObject(value = """
                            {
                                "status": 401,
                                "message": "Token not found, expired, or revoked"
                            }
                            """)))
    })
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        return ResponseEntity.ok(authService.refresh(request));
    }
}