package com.product_manager.ws.security;

import com.product_manager.ws.model.Users;
import com.product_manager.ws.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository; // if you want to load more details from DB

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Get the Authorization header
        String authHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            // If no token is present, continue the filter chain
            filterChain.doFilter(request, response);
            return;
        }
        // 2. Extract the token (remove the "Bearer " prefix)
        String token = authHeader.substring(7);
        try {
            // 3. Extract username from the token
            String username = jwtService.extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Validate the token against the username
                if (jwtService.isTokenValid(token, username)) {
                    // 4. Extract the role from the token claims
                    // Here we assume that when you generated the token,
                    // you added the "role" claim (e.g., "ADMIN" or "USER").
                    Claims claims = jwtService.extractAllClaims(token);
                    String roleClaim = claims.get("role", String.class);
                    // Build the proper authority using the prefix "ROLE_"
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    if (roleClaim != null) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + roleClaim));
                    } else {
                        // Fallback if no role is provided
                        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                    }

                    // 5. Create an authentication token with the proper authorities.
                    UserDetails userDetails = User.withUsername(username)
                            .password("") // Password not required here
                            .authorities(authorities)
                            .build();

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    // 6. Set the authentication in the security context
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtException ex) {
            // Log the exception if needed.
            // For now, we continue without setting authentication.
        }
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
