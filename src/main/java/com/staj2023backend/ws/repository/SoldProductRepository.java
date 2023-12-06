package com.staj2023backend.ws.repository;

import com.staj2023backend.ws.model.Product;
import com.staj2023backend.ws.model.Selling;
import com.staj2023backend.ws.model.SoldProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SoldProductRepository extends JpaRepository<SoldProduct, Long> {

    List<SoldProduct> findAll();

    Optional<SoldProduct> findById(Long id);

    List<SoldProduct> findByProductId(Long id);

    void delete(SoldProduct soldProduct);

}
