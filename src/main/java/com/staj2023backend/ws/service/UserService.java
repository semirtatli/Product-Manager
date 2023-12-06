package com.staj2023backend.ws.service;

import com.staj2023backend.ws.model.Users;
import com.staj2023backend.ws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

//Servis classını @Service annotationuyla belirtiyoruz
//UserRepository deki data için encapsulation sağlıyor
@Service
public class UserService {
    //UserRepository icindeki metodlara UserService uzerinden erismek icin dependency injection
    @Autowired
    UserRepository userRepository;


    PasswordEncoder passwordEncoder;


    //UserRepository icindeki metodlara UserService uzerinden erismek icin metodlar

//Kullanıcının şifresi database'de yalın şekilde duruyor
//password Encoder ile password encode edilerek databasade tutuluyor


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void save(Users user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

    }

    public List<Users> findAll(){
        return userRepository.findAll();
    }

    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    public void delete(Users user) {
        userRepository.delete(user);
    }
}
