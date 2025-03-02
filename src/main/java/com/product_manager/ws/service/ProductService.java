package com.product_manager.ws.service;

import com.product_manager.ws.model.Product;
import com.product_manager.ws.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Servis classını @Service annotationuyla belirtiyoruz
//ProductRepository deki data için encapsulation sağlıyor
@Service
public class ProductService {
    //ProductRepository icindeki metodlara ProductService uzerinden erismek icin dependency injection
    @Autowired
    ProductRepository productRepository;

    //ProductRepository icindeki metodlara ProductService uzerinden erismek icin metodlar
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

}
