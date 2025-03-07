package com.product_manager.ws.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    //application.propertiesten inject et
    @Value("${jwt.secret}")
    private String secretKey; // e.g., "MySuperSecretKeyForJwtExampleMySuperSecretKeyForJwtExample" (min 256 bits for HS256)

    @Value("${jwt.expiration}")
    private long jwtExpirationInMillis; // e.g., 86400000 (24 hours in milliseconds)

    /**
     * Generate a JWT token using the provided username and role.
     *
     * @param username the username for which to generate the token
     * @param role the role of the user (e.g., "USER" or "ADMIN")
     * @return a signed JWT token as a String
     */
    public String generateToken(String username, String role) {
        // Create a claims map and add custom claims (like user role)
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); // we include the user role

        return createToken(claims, username);
    }

    /**
     * Create the JWT token with the given claims and subject.
     *
     * @param claims a Map of claims to include in the token
     * @param subject typically the username
     * @return a signed JWT token as a String
     */
    private String createToken(Map<String, Object> claims, String subject) {
        // Convert the secret key into a Key object for signing.
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        // Build the token with claims, subject, issued at, expiration, and sign it.
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // subject is usually the username
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validate the token by checking its signature, expiration, and subject.
     *
     * @param token the JWT token to validate
     * @param username the expected username (subject)
     * @return true if valid, false otherwise
     */
    public boolean isTokenValid(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    /**
     * Extract the username (subject) from the token.
     *
     * @param token the JWT token
     * @return the username contained in the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Check if the token is expired.
     *
     * @param token the JWT token
     * @return true if expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extract the expiration date from the token.
     *
     * @param token the JWT token
     * @return the expiration date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract a specific claim using a claim resolver function.
     *
     * @param token the JWT token
     * @param claimsResolver a function to resolve a claim from the Claims object
     * @param <T> the type of the claim
     * @return the resolved claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parse and extract all claims from the token.
     *
     * @param token the JWT token
     * @return the Claims object with all claims from the token
     */
    Claims extractAllClaims(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
