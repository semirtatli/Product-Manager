package com.staj2023backend.ws.auth;

import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonView;
import com.staj2023backend.ws.shared.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.staj2023backend.ws.error.ApiError;
import com.staj2023backend.ws.model.Users;
import com.staj2023backend.ws.repository.UserRepository;

@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/api/1.0/auth")
    @JsonView(Views.Base.class)
    ResponseEntity<?> handleAuthentication(@RequestHeader(name="Authorization", required = false) String authorization) {
        if(authorization == null) {
            // Eğer Authorization header yoksa 401 Unauthorized hata dön
            ApiError error = new ApiError(401, "Unauthorized request", "/api/1.0/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        // Basic authentication için gelen Authorization header'ı işle
        String base64encoded = authorization.split("Basic ")[1]; // dXNlcjE6UDRzc3dvcmQ=
        String decoded = new String(Base64.getDecoder().decode(base64encoded)); // user1:P4ssword
        String[] parts = decoded.split(":"); // ["user1", "P4ssword"]
        String username = parts[0];
        String password = parts[1];
        Users inDB = userRepository.findByUsername(username);
        // Kullanıcı mevcut değilse 404 response dön
        if (inDB == null) {
            ApiError error = new ApiError(404, "User not found", "/api/1.0/auth");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        // Kullanıcının girilen şifresi ile veritabanındaki şifresi karşılaştır
        String hashedPassword = inDB.getPassword();
        if(!passwordEncoder.matches(password, hashedPassword)) {
            // Şifre uyumsuzsa 401 Unauthorized hata dön
            ApiError error = new ApiError(401, "Unauthorized request", "/api/1.0/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
// Doğrulama başarılı ise kullanıcıyı response olarak dön
        return ResponseEntity.ok(inDB);
    }

}