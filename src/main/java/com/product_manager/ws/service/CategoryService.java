package com.product_manager.ws.service;




import com.product_manager.ws.repository.CategoryRepository;
import com.product_manager.ws.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

//Servis classını @Service annotationuyla belirtiyoruz
//UserRepository deki data için encapsulation sağlıyor
@Service
public class CategoryService {
    //CategoryRepository icindeki metodlara CategoryService uzerinden erismek icin dependency injection
    @Autowired
    CategoryRepository categoryRepository;
//CategoryRepository icindeki metodlara CatgegoryService uzerinden erismek icin metodlar

    //Kategori Repository'e kayıt edilir
    public void save(Category category) {

        categoryRepository.save(category);

    }
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }
}