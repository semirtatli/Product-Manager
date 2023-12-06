package com.staj2023backend.ws.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.staj2023backend.ws.repository.UserRepository;
import com.staj2023backend.ws.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Base64;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthController authController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Users testUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        // Create a test user
        testUser = new Users(1, "user1", "User One", "P4ssword");
        String hashedPassword = new BCryptPasswordEncoder().encode(testUser.getPassword());
        testUser.setPassword(hashedPassword);
    }

    @Test
    public void handleAuthentication_validCredentials_returnsUser() throws Exception {
        // Mock the behavior of userRepository.findByUsername
        doReturn(testUser).when(userRepository).findByUsername(Mockito.any(String.class));

        String credentials = "user1:P4ssword";
        String base64encoded = Base64.getEncoder().encodeToString(credentials.getBytes());

        mockMvc.perform(
                        post("/api/1.0/auth")
                                .header("Authorization", "Basic " + base64encoded)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void handleAuthentication_invalidCredentials_returnsUnauthorized() throws Exception {
        // Mock the behavior of userRepository.findByUsername
        doReturn(testUser).when(userRepository).findByUsername(Mockito.any(String.class));

        String credentials = "user1:WrongPassword";
        String base64encoded = Base64.getEncoder().encodeToString(credentials.getBytes());

        mockMvc.perform(
                        post("/api/1.0/auth")
                                .header("Authorization", "Basic " + base64encoded)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void handleAuthentication_userNotFound_returnsNotFound() throws Exception {
        // Mock the behavior of userRepository.findByUsername
        doReturn(null).when(userRepository).findByUsername(Mockito.any(String.class));

        String credentials = "nonexistentuser:P4ssword";
        String base64encoded = Base64.getEncoder().encodeToString(credentials.getBytes());

        mockMvc.perform(
                        post("/api/1.0/auth")
                                .header("Authorization", "Basic " + base64encoded)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}