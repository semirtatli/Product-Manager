package com.staj2023backend.ws.unique_username;

import com.staj2023backend.ws.model.Users;
import com.staj2023backend.ws.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class UniqueUsernameValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UniqueUsernameValidator uniqueUsernameValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void isValid_uniqueUsername_returnsTrue() {
        String username = "newUsername";

        // Mock the behavior of userRepository.findByUsername
        when(userRepository.findByUsername(username)).thenReturn(null);

        boolean isValid = uniqueUsernameValidator.isValid(username, null);

        assert isValid; // The username is unique, so the validation should pass
    }

    @Test
    public void isValid_existingUsername_returnsFalse() {
        String existingUsername = "existingUser";

        // Mock the behavior of userRepository.findByUsername
        when(userRepository.findByUsername(existingUsername)).thenReturn(new Users());

        boolean isValid = uniqueUsernameValidator.isValid(existingUsername, null);

        assert !isValid; // The username already exists, so the validation should fail
    }
}