package com.product_manager.ws.auth;

import com.product_manager.ws.model.Users;
import com.product_manager.ws.repository.UserRepository;
import com.product_manager.ws.security.JwtService;
import com.product_manager.ws.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/1.0/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Users inDB = userRepository.findByUsername(request.getUsername());
        if (inDB == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new GenericResponse("User not found"));
        }
        if (!passwordEncoder.matches(request.getPassword(), inDB.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new GenericResponse("Invalid credentials"));
        }

        String token = jwtService.generateToken(inDB.getUsername(), inDB.getRole().name());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    // DTOs for clarity
    public static class AuthRequest {
        private String username;
        private String password;
        // Getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class AuthResponse {
        private String token;
        public AuthResponse(String token) { this.token = token; }
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }
}
