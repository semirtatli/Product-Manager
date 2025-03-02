package com.product_manager.ws.repository;


import com.product_manager.ws.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
//JPA Repository database ile uğraşmadan kod içerisinde SQL query lerinin yapacağı işi yaparak işimizi kolaylaştırıyor
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAll();

    Optional<Category> findById(Long id);

    void delete(Category category);
}