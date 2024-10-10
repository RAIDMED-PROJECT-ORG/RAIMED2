package fr.imt.raimed2.security.service;


import fr.imt.raimed2.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final SecretKey secretKey;

    @Value("${application.security.jwt.expiration.access-token}")
    private long accessTokenExpiration;
    @Value("${application.security.jwt.expiration.refresh-token}")
    private long refreshTokenExpiration;

    public JwtService() {
        // Generate a secret key for HS512 algorithm
        secretKey = Jwts.SIG.HS512.key().build();
    }

    /**
     * This method extract the username from a jwt token
     * @param token the jwt token
     * @return the username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * This method extract the expiration date from a jwt token
     * @param token the jwt token
     * @return the expiration date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * This method extract a claim from a jwt token according to a resolver function
     * @param token the jwt token
     * @param claimsResolver a function that proceed the extraction of the claim
     * @return the value of the claim
     * @param <T> the return type of the resolver function
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * This method extract all claims from a jwt token
     * @param token the jwt token
     * @return the claims
     */
    private Claims extractAllClaims(String token) throws SignatureException {
        // Parsing the token with the secret key and returning the payload of the token
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * This method generate a jwt token from a UserDetails object
     * @param user the User object
     * @return the jwt token
     */
    public String generateAccessToken(User user) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("firstname", user.getFirstname());
        claims.put("lastname", user.getLastname());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());
        return buildToken(claims, user, accessTokenExpiration);
    }

    /**
     * This method generate a refresh token from a UserDetails object
     * @param user the User object
     * @return the refresh token
     */
    public String generateRefreshToken(User user) {
        return buildToken(new HashMap<>(), user, refreshTokenExpiration);
    }

    /**
     * This method build a jwt token from a UserDetails object, claims given as a map and an expiration time
     * @param extraClaims the claims to add to the payload of the token
     * @param user the User object
     * @param expiration the expiration time of the token in milliseconds
     * @return the generated jwt token a string
     */
    private String buildToken(Map<String, Object> extraClaims, User user, long expiration) {
        return Jwts
                .builder()
                .issuer("raimed")
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    /**
     * This method check if a jwt token is valid for a given UserDetails object
     * @param token the jwt token
     * @param userDetails the UserDetails object
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValidForUser(String token, UserDetails userDetails) {
        // Extracting username from token
        final String username = extractUsername(token);
        // Checking if the username extracted from the token is the same as the one from the UserDetails object
        // and if the token is not expired
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * This method check if a jwt token is expired
     * @param token the jwt token
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

}
