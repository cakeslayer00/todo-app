package io.cakeslayer.backend.security.authentication.impl;

import io.cakeslayer.backend.dto.request.LoginRequest;
import io.cakeslayer.backend.dto.request.RefreshRequest;
import io.cakeslayer.backend.dto.request.RegisterRequest;
import io.cakeslayer.backend.dto.response.AuthResponse;
import io.cakeslayer.backend.entity.RefreshToken;
import io.cakeslayer.backend.entity.User;
import io.cakeslayer.backend.exception.UserAlreadyExistsException;
import io.cakeslayer.backend.repository.UserRepository;
import io.cakeslayer.backend.security.authentication.AuthService;
import io.cakeslayer.backend.security.authentication.RefreshTokenService;
import io.cakeslayer.backend.security.jwt.JwtService;
import io.cakeslayer.backend.security.token.RefreshTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String ERR_USER_ALREADY_EXISTS = "Username or email already exists";

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        try {
            userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException(ERR_USER_ALREADY_EXISTS);
        }
        log.info("New user '{}' is created", request.username());
        return buildAuthResponse(user);
    }

    @Override
    @Transactional
    public AuthResponse authenticate(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password())
        );

        User user = (User) auth.getPrincipal();

        return buildAuthResponse(user);
    }

    @NonNull
    private AuthResponse buildAuthResponse(User user) {
        String plainToken = RefreshTokenUtils.generateRefreshToken();

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user, plainToken);
        String accessToken = jwtService.generateToken(user, refreshToken.getId());

        log.info("User '{}' is authenticated", user.getUsername());
        return new AuthResponse(user.getUsername(), accessToken, plainToken);
    }

    @Override
    @Transactional
    public AuthResponse refresh(RefreshRequest request) {
        RefreshToken refreshToken = refreshTokenService.validateAndRevoke(request.refreshToken());

        User user = refreshToken.getUser();

        String plainToken = RefreshTokenUtils.generateRefreshToken();

        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user, plainToken, refreshToken.getFamilyId());
        String accessToken = jwtService.generateToken(user, newRefreshToken.getId());

        return new AuthResponse(user.getUsername(), accessToken, plainToken);
    }
}
