package com.product_manager.ws.repository;


import com.product_manager.ws.model.Selling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
//JPA Repository database ile uğraşmadan kod içerisinde SQL query lerinin yapacağı işi yaparak işimizi kolaylaştırıyor
@Repository
public interface SellingRepository extends JpaRepository<Selling, Long> {

    List<Selling> findAll();

    Optional<Selling> findById(Long id);

}
