package fr.imt.raimed2.security.controller;

import fr.imt.raimed2.security.dto.AuthenticationJwts;
import fr.imt.raimed2.security.dto.request.LoginDto;
import fr.imt.raimed2.security.dto.request.SignUpDto;
import fr.imt.raimed2.security.dto.response.LoginResponse;
import fr.imt.raimed2.security.dto.response.RegisterReponse;
import fr.imt.raimed2.security.service.AuthenticationService;
import fr.imt.raimed2.user.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("signup")
    public ResponseEntity<RegisterReponse> register(@Valid @RequestBody SignUpDto signUpDto) {
        User user = authenticationService.register(signUpDto);
        return ResponseEntity.ok(
                RegisterReponse
                        .builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .email(user.getEmail())
                        .build()
        );
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(HttpServletResponse response, @Valid @RequestBody LoginDto loginDto) {
        // Login user by generating access token and refresh token
        AuthenticationJwts jwts = authenticationService.login(loginDto);
        // Generate refresh token cookie to attach refresh token
        authenticationService.generateRefreshTokenCookie(response, jwts.getRefreshToken());
        // Return access token to client in body
        return ResponseEntity.ok(LoginResponse.builder().accessToken(jwts.getAccessToken()).build());
    }

    @PostMapping("refresh")
    public ResponseEntity<LoginResponse> refresh(HttpServletRequest request, HttpServletResponse response) {
        // Extract refresh token from cookie
        Cookie refreshTokenCookie = WebUtils.getCookie(request, "refreshToken");
        if (refreshTokenCookie == null) {
            // If no refresh token cookie is found, return bad request response
            return ResponseEntity.badRequest().build();
        }
        try {
            // Refresh user by generating new access token and refresh token
            AuthenticationJwts jwts = authenticationService.refresh(refreshTokenCookie.getValue());
            // Generate refresh token cookie to attach refresh token
            authenticationService.generateRefreshTokenCookie(response, jwts.getRefreshToken());
            // Return access token to client in body
            return ResponseEntity.ok(LoginResponse.builder().accessToken(jwts.getAccessToken()).build());
        } catch (Exception e) {
            // Remove refresh token cookie from client as it is invalid or expired
            authenticationService.deleteRefreshTokenCookie(response);
            // If the refresh token process is invalid, return bad request response
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("logout")
    public ResponseEntity<String> logout(HttpServletResponse response, @AuthenticationPrincipal User user) {
        // Logout user by removing refresh token from database
        authenticationService.logout(user);
        // Remove refresh token cookie from client
        authenticationService.deleteRefreshTokenCookie(response);
        return ResponseEntity.ok("User logged out successfully");
    }

}
