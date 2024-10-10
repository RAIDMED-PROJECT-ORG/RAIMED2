package fr.imt.raimed2.security.service;

import fr.imt.raimed2.security.dto.AuthenticationJwts;
import fr.imt.raimed2.security.dto.request.LoginDto;
import fr.imt.raimed2.security.dto.request.SignUpDto;
import fr.imt.raimed2.security.model.RefreshToken;
import fr.imt.raimed2.security.repository.RefreshTokenRepository;
import fr.imt.raimed2.user.exception.EmailAlreadyInUseException;
import fr.imt.raimed2.user.exception.UsernameAlreadyInUseException;
import fr.imt.raimed2.user.model.Role;
import fr.imt.raimed2.user.model.User;
import fr.imt.raimed2.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Value("${application.security.jwt.expiration.refresh-token}")
    private long refreshExpiration;

    public User register(SignUpDto signUpDto) throws UsernameAlreadyInUseException, EmailAlreadyInUseException {

        // Checking if username is already taken
        if(userRepository.existsByUsername(signUpDto.getUsername())) {
            throw new UsernameAlreadyInUseException(signUpDto.getUsername());
        }

        // Checking if email is already taken
        if(userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new EmailAlreadyInUseException(signUpDto.getEmail());
        }

        // Creating user
        return userRepository.save(
            User.builder()
                .username(signUpDto.getUsername())
                .firstname(signUpDto.getFirstname())
                .lastname(signUpDto.getLastname())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .role(Role.STUDENT)
                .build()
        );
    }

    /**
     * This method is used to log in a user by validating sent credentials and generating
     * access token and refresh token for the user
     * @param loginDto the login dto object containing the credentials of the user
     * @return the authentication jwts object containing the access token and the refresh token
     */
    public AuthenticationJwts login(LoginDto loginDto) {
        // Authenticating user with username and password
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
            )
        );

        // Retrieving user from authentication object
        User user = (User) authentication.getPrincipal();

        // Generating access token and refresh token
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Revoking existing user refresh token
        revokeUserRefreshToken(user);

        // Saving new refresh token
        refreshTokenRepository.save(
            RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .build()
        );

        // Returning both tokens
        return AuthenticationJwts.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * This method is used to refresh the expired access token of a user by generating a new one
     * according to the refresh token sent by the client : verifying the signature and the expiration date of the token
     * and checking if the refresh token is the same as the one stored in the database
     * @param refreshToken the refresh token sent by the client
     * @return the authentication jwts object containing the new access token and the new refresh token
     */
    public AuthenticationJwts refresh(String refreshToken) {

        // Extracting username from refresh token payload
        String username = jwtService.extractUsername(refreshToken);

        // Retrieving user from database according to the username extracted from the refresh token
        User user = userRepository.findByUsername(username).orElseThrow();

        if(!jwtService.isTokenValidForUser(refreshToken, user)) {
            throw new RuntimeException("Invalid refresh token");
        }

        // Retrieving refresh token stored in the database according to the user
        RefreshToken savedRefreshToken = refreshTokenRepository.findByUser(user).orElseThrow();

        // Checking if the refresh token stored in the database is the same as the one sent by the client
        if(!savedRefreshToken.getToken().equals(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        // Revoking current user refresh token stored in the database
        revokeUserRefreshToken(user);

        // Generating new refresh token
        String newRefreshToken = jwtService.generateRefreshToken(user);

        // Saving new refresh token in the database
        refreshTokenRepository.save(
            RefreshToken.builder()
                .token(newRefreshToken)
                .user(user)
                .build()
        );

        // Generating new access token & returning both tokens
        return AuthenticationJwts.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(newRefreshToken)
                .build();
    }

    /**
     * This method is used to log out a user by revoking its refresh token stored in the database
     * @param user the user whose refresh token will be revoked
     */
    public void logout(User user) {
        revokeUserRefreshToken(user);
    }

    /**
     * This method is used to revoke refresh tokens of a user by deleting it from the database
     * as the refresh process use refresh tokens stored in the database to ensure its validity
     * even if the signature and the expiration date are valid on the jwt token.
     * @param user the user whose refresh token will be revoked
     */
    public void revokeUserRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user);
        refreshTokenRepository.flush();
    }

    /**
     * This method is used to generate the refresh token cookie and send it to the client side
     * @param response the HttpServletResponse object which will be sent back to the client
     * @param refreshToken the refresh token to set as a value of the cookie
     */
    public void generateRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setMaxAge((int) (refreshExpiration / 1000));
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/v1/auth/");
        response.addCookie(cookie);
    }

    /**
     * This method is used to delete the refresh token cookie from the client side
     * @param response the HttpServletResponse object which will be sent back to the client
     */
    public void deleteRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", "");
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/v1/auth/");
        response.addCookie(cookie);
    }

}
