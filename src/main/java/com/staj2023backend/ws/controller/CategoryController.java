package com.staj2023backend.ws.controller;

import com.staj2023backend.ws.model.Category;
import com.staj2023backend.ws.service.CategoryService;
import com.staj2023backend.ws.model.Product;
import com.staj2023backend.ws.service.ProductService;
import com.staj2023backend.ws.model.ProductID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

// ProductController sınıfı, ürünler ve kategorilerle ilgili HTTP isteklerine yanıt veren REST API işlemlerini gerçekleştirir.
@RestController
public class CategoryController {
    // ProductService ve CategoryService sınıflarını otomatik olarak enjekte eder.


    @Autowired
    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Yeni bir category oluşturan HTTP POST request
    @PostMapping("api/category")
    public ResponseEntity<ProductID> createCategory(@RequestBody Category category) {
        System.out.println(category);
        //change this
        ProductID result = new ProductID(UUID.randomUUID().toString());
        //CategoryRepository e yeni category' i kaydetme
        categoryService.save(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }


    //Tum categor'leri categoryService uzerinden categoryRepository deki findAll ile listeleme
    @GetMapping("api/category")
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }


    //Id ile delete request atarak service ve repository üzerinden category silme
    @DeleteMapping("api/category/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        Optional<Category> categoryToDelete = categoryService.findById(id);
//Category mevcut ise 200 OK degil ise 404 NOT FOUND
        if (categoryToDelete.isPresent()) {
            categoryService.delete(categoryToDelete.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}





