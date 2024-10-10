package fr.imt.raimed2.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.imt.raimed2.exception.dto.response.Raimed2ErrorDetailsDto;
import fr.imt.raimed2.security.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        @NonNull  HttpServletResponse response,
        @NonNull  FilterChain filterChain
    ) throws ServletException, IOException {

        if (
            request.getRequestURI().contains("/api/v1/auth/login")
            || request.getRequestURI().contains("/api/v1/auth/signup")
            || request.getRequestURI().contains("/api/v1/auth/refresh")
                || request.getRequestURI().contains("/api/v1/virtual-patient/xml")
        ) {
            // If the request is an auth request except for logout, we don't need to check the token
            filterChain.doFilter(request, response);
            return;
        }

        // Get the Authorization header value
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null ||!authorizationHeader.startsWith("Bearer ")) {
            // If the token is null or doesn't start with Bearer, we don't need to check the token
            // We just continue the filter chain to the next filter
            configureErrorResponse(
                    "Missing or invalid Authorization header",
                    HttpStatus.BAD_REQUEST.value(),
                    request,
                    response
            );
            return;
        }

        // Extract the jwt token from the header value
        String jwt = authorizationHeader.substring(7);
        String username;

        try {
            // Extract the username from the jwt token payload)
            username = jwtService.extractUsername(jwt);
        } catch (ExpiredJwtException e) {
            // If the token is expired, we don't need to check the token
            // We just continue the filter chain to the next filter
            configureErrorResponse(
                    "Expired access token",
                    HttpStatus.UNAUTHORIZED.value(),
                    request,
                    response
            );
            return;
        } catch (SignatureException | MalformedJwtException e) {
            // If the token is invalid, we don't need to check the token
            // We just continue the filter chain to the next filter
            configureErrorResponse(
                    "Invalid access token",
                    HttpStatus.UNAUTHORIZED.value(),
                    request,
                    response
            );
            return;
        }

        // Check if the username is not null and if the user is not already in the authentication context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load the user from the database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValidForUser(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * This method configure the error response that is sent in the body of the response
     * @param details the error details
     * @param statusCode the status code of the response
     * @param request the request
     * @param response the response
     * @throws IOException if an error occurs while writing the response
     */
    private void configureErrorResponse(
            String details,
            int statusCode,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        Raimed2ErrorDetailsDto errorDetails = Raimed2ErrorDetailsDto.builder()
                .message("Authentication Error")
                .details(details)
                .path(request.getRequestURI())
                .build();
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), errorDetails);
    }

}
