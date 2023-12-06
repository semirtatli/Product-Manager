package com.staj2023backend.ws.controller;

import com.staj2023backend.ws.error.ApiError;
import com.staj2023backend.ws.shared.GenericResponse;
import com.staj2023backend.ws.service.UserService;
import com.staj2023backend.ws.model.Users;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// UserController sınıfı, kullanıcılarla ilgili HTTP isteklerine yanıt veren REST API işlemlerini gerçekleştirir.
@RestController
public class UserController {

//@Autowired annotation'u automatic dependency injection için kullanılır
// Spring'in otomatik olarak dependency'i bulup Spring bean'ine manual gerektirmeden enjekte etmesini sağlar
@Autowired
UserService userService;


    //HTTP üzerinde POST requestleri ileten ve client'tan server'e veri aktaran yapı
// /api/1.0/users url sine veri gönderiyor
    @PostMapping("/api/1.0/users")
//RequestBody request üzerindeki JSON datasını Users objesi ile bağdaştırıyor
//Valid bu request'in geçerliliğini kontrol ediyor
    public GenericResponse createUser(@Valid @RequestBody Users user) {
    //Eğer request ve data uygunsa Service üzerinden User Repository'sine ve dolaylı olarak H2 databasesine yeni user kaydediliyor
        userService.save(user);
//201 Created response'u dönerse sistem üzerinden user created mesajı dönüyor
        return new GenericResponse("user created");
    }
//HTTP GET request ile server üzerinden veri alır
    @GetMapping("/api/1.0/users")
    public List<Users> getAllUsers() {
//User Repository'sinde bulunan tüm veriyi List üzerinde return eder
        return userService.findAll();
    }

    @GetMapping("api/1.0/users/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Optional<Users> users = userService.findById(id);

        if (users.isPresent()) {
            return ResponseEntity.ok(users.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//PATCH request ile id'si belirtilen User'ın özellikleri değiştirilebilir
    @PatchMapping("api/1.0/users/{id}")
    public ResponseEntity<Users> updateUserById(@PathVariable Long id, @RequestBody Users updatedUser) {
//id ile User Repository içinde bulunur
        Optional<Users> existingUser = userService.findById(id);
//User mevcut ise request ile güncellenen veriler mevcut user üserinde değiştirilir
        if (existingUser.isPresent()) {
            Users userToUpdate = existingUser.get();
            userToUpdate.setUsername(updatedUser.getUsername());
            userToUpdate.setDisplayName(updatedUser.getDisplayName());
            userToUpdate.setPassword(updatedUser.getPassword());
//User Repository üzerine güncellenmiş User kaydedilir
            userService.save(userToUpdate);

            return ResponseEntity.ok(userToUpdate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//DELETE requesti ile id'si belirtilen User Repository üzerinden silinir
    @DeleteMapping("api/1.0/users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        Optional<Users> userToDelete = userService.findById(id);

        if (userToDelete.isPresent()) {
//Mevcut ise
            userService.delete(userToDelete.get());   //User silinir
            return ResponseEntity.ok().build();      //200 OK yaniti
        } else {
//Mevcut değilse 404 response'u dönülür
            return ResponseEntity.notFound().build();
        }
    }

//Herhangi bir Users alanı yanlış girilince hangi hata mesajını vereceğini bulan ve mesajı veren metod
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException exception) {
        ApiError error = new ApiError(400, "Validation error", "/api/1.0/users");
        Map<String, String> validationErrors = new HashMap<>();

        for(FieldError fieldError: exception.getBindingResult().getFieldErrors()){
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        error.setValidationErrors(validationErrors);
        return error;
    }

}
