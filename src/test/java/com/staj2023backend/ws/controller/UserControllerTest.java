package com.staj2023backend.ws.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.staj2023backend.ws.WsApplication;
import com.staj2023backend.ws.controller.UserController;
import com.staj2023backend.ws.model.Users;
import com.staj2023backend.ws.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = {WsApplication.class, UserController.class})
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;


    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    Users RECORD_1 = new Users(1, "Elresheph", "Elreseph", "P4ssword");
    Users RECORD_2 = new Users(2, "Reiner", "Braun", "ForEldoia5");
    Users RECORD_3 = new Users(3, "Chrollo", "Lucifer", "Requiem8");

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void getUserById_existingUser_returnsUser() throws Exception {

        // Mock the behavior of userRepository.findById
        when(userService.findById(Mockito.any(Long.class))).thenReturn(Optional.of(RECORD_1));

        System.out.println(userService.findById(1L));


        mockMvc.perform(
                        get("/api/1.0/users/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Elresheph"));
    }

    @Test
    public void getAllUsers_success() throws Exception {
        List<Users> users = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
        Mockito.when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/api/1.0/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.length()").value(users.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value(RECORD_1.getUsername()))
                .andExpect(jsonPath("$[1].id").value(RECORD_2.getId()))
                .andExpect(jsonPath("$[1].username").value(RECORD_2.getUsername()))
                .andExpect(jsonPath("$[2].id").value(RECORD_3.getId()))
                .andExpect(jsonPath("$[2].username").value(RECORD_3.getUsername()));
    }


    @Test
    public void createUser_success() throws Exception {
        Users newUser = new Users(4, "NewUserr", "NewUserrrr", "NewPassword45");

        // Mock the behavior of userService.save
        doNothing().when(userService).save(any(Users.class));

        String userJson = objectMapper.writeValueAsString(newUser);

        mockMvc.perform(
                        post("/api/1.0/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("user created"));
    }



    @Test
    public void getUserById_nonExistingUser_returnNotFound() throws Exception {
        Mockito.when(userService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/1.0/users/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



    @Test
    public void updateUserById_existingUser_returnUpdatedUser() throws Exception {
        Users updatedUser = new Users(1, "newUsername", "newDisplayName", "newPassword45");
        Mockito.when(userService.findById(Mockito.any(Long.class))).thenReturn(Optional.of(RECORD_1));

        doNothing().when(userService).save(any(Users.class));

        String userJson = objectMapper.writeValueAsString(updatedUser);

        mockMvc.perform(patch("/api/1.0/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("newUsername"))
                .andExpect(jsonPath("$.displayName").value("newDisplayName"))
                .andExpect(jsonPath("$.password").value("newPassword45"));
    }

        @Test
    public void deleteUserById_existingUser_returnOk() throws Exception {

        Mockito.when(userService.findById(Mockito.any(Long.class))).thenReturn(Optional.of(RECORD_1));

        mockMvc.perform(delete("/api/1.0/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

        @Test
    public void deleteUserById_nonExistingUser_returnNotFound() throws Exception {
        Mockito.when(userService.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/1.0/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void handleValidationException_returnsValidationErrors() throws Exception {
        String invalidUserJson = "{\"username\": \"\", \"displayName\": \"\", \"password\": \"\"}";

        mockMvc.perform(post("/api/1.0/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidUserJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.path").value("/api/1.0/users"))
                .andExpect(jsonPath("$.validationErrors.username").exists())
                .andExpect(jsonPath("$.validationErrors.displayName").exists())
                .andExpect(jsonPath("$.validationErrors.password").exists());
    }


}
//

//





