package com.product_manager.ws.service;

import com.product_manager.ws.model.SoldProduct;
import com.product_manager.ws.repository.SoldProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

//Servis classını @Service annotationuyla belirtiyoruz
//SellingRepository deki data için encapsulation sağlıyor
@Service
public class SoldProductService {
    //SellingRepository icindeki metodlara SellingService uzerinden erismek icin dependency injection
    @Autowired
    SoldProductRepository soldProductRepository;

    //SellingRepository icindeki metodlara SellingService uzerinden erismek icin metodlar
    public void save(SoldProduct soldProduct) {soldProductRepository.save(soldProduct);}
    public List<SoldProduct> findAll(){
        return soldProductRepository.findAll();
    }
    public Optional<SoldProduct> findById(Long id) {
        return soldProductRepository.findById(id);
    }
    public void delete(SoldProduct soldProduct) {
        soldProductRepository.delete(soldProduct);
    }
    public List<SoldProduct> findByProductId(Long id) {return soldProductRepository.findByProductId(id);}

}