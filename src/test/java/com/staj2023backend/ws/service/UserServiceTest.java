package com.staj2023backend.ws.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.staj2023backend.ws.model.Users;
import com.staj2023backend.ws.repository.UserRepository;
import com.staj2023backend.ws.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveUser_success() {
        Users newUser = new Users();

        newUser.setUsername("newUser");
        newUser.setDisplayName("New User");
        newUser.setPassword("NewPassword");
        newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
        userService.save(newUser);
        verify(userRepository).save(newUser);
    }

    @Test
    public void findAllUsers_success() {
        Users user1 = new Users();
        user1.setUsername("user1");
        Users user2 = new Users();
        user2.setUsername("user2");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<Users> users = userService.findAll();

        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user2", users.get(1).getUsername());
    }

    @Test
    public void findUserById_existingUser_returnsUser() {
        Users existingUser = new Users();
        existingUser.setId(1L);
        existingUser.setUsername("existingUser");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        Optional<Users> user = userService.findById(1L);

        assertTrue(user.isPresent());
        assertEquals("existingUser", user.get().getUsername());
    }

    @Test
    public void findUserById_nonExistingUser_returnsEmpty() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Users> user = userService.findById(99L);

        assertFalse(user.isPresent());
    }

    @Test
    public void testDeleteExistingUser() {
        // Mock the behavior of userRepository.findById
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new Users()));

        // Call the delete method
        userService.delete(new Users());

        // Verify that userRepository.delete was called
        verify(userRepository, times(1)).delete(any(Users.class));
    }

}
