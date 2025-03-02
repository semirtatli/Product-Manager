package com.product_manager.ws.service;


import com.product_manager.ws.model.Selling;
import com.product_manager.ws.repository.SellingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

//Servis classını @Service annotationuyla belirtiyoruz
//SellingRepository deki data için encapsulation sağlıyor
@Service
public class SellingService {
    //SellingRepository icindeki metodlara SellingService uzerinden erismek icin dependency injection
    @Autowired
    SellingRepository sellingRepository;

    //SellingRepository icindeki metodlara SellingService uzerinden erismek icin metodlar
    public void save(Selling selling) {sellingRepository.save(selling);}
    public List<Selling> findAll(){
        return sellingRepository.findAll();
    }
    public Optional<Selling> findById(Long id) {
        return sellingRepository.findById(id);
    }

}
