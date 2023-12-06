package com.staj2023backend.ws.unique_username;

import com.staj2023backend.ws.model.Users;
import com.staj2023backend.ws.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
//otomatik dependency injection
    @Autowired
UserRepository userRepository;
//girilen username kullanılıyorsa isValid false döndürüyor
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if(userRepository!=null){
            Users user = userRepository.findByUsername(username);
            if(user != null){
                return false;
            }
        }
        return true;
    }

}
