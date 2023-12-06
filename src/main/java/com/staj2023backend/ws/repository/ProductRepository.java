package com.staj2023backend.ws.repository;

import com.staj2023backend.ws.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
//JPA Repository database ile uğraşmadan kod içerisinde SQL query lerinin yapacağı işi yaparak işimizi kolaylaştırıyor
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    void delete(Product product);

}
