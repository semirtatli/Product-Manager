package com.staj2023backend.ws.repository;

import com.staj2023backend.ws.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//JPA Repository database ile uğraşmadan kod içerisinde SQL query lerinin yapacağı işi yaparak işimizi kolaylaştırıyor
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByUsername(String username);

    List<Users> findAll();

    Optional<Users> findById(Long id);

    void delete(Users user);

}
