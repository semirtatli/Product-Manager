package com.staj2023backend.ws.controller;

import com.staj2023backend.ws.model.Category;
import com.staj2023backend.ws.model.SoldProduct;
import com.staj2023backend.ws.service.CategoryService;
import com.staj2023backend.ws.model.Product;
import com.staj2023backend.ws.service.ProductService;
import com.staj2023backend.ws.model.ProductID;
import com.staj2023backend.ws.service.SoldProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

// ProductController sınıfı, ürünler ve kategorilerle ilgili HTTP isteklerine yanıt veren REST API işlemlerini gerçekleştirir.
@RestController
public class ProductController {
    // ProductService ve CategoryService sınıflarını otomatik olarak enjekte eder.

    @Autowired
    ProductService productService;

    @Autowired
    SoldProductService soldProductService;

    @Autowired
    public ProductController(ProductService productService , SoldProductService soldProductService) {
        this.productService =  productService;
        this.soldProductService = soldProductService;
    }

    // Tüm ürünleri listeleyen HTTP GET request

    @GetMapping("api/products")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    // Belirli bir kategorideki urunleri listeler
    @GetMapping("/api/products/byCategory/{ProductCategoryID}")
    public List<Product> getProductByCategory(@PathVariable Long ProductCategoryID) {
        List<Product> products = productService.findAll();
        List<Product> filteredProducts = new ArrayList<>();

        for (Product product : products) {
            if (product.getProductCategoryID().equals(ProductCategoryID)) {
                filteredProducts.add(product);
            }
        }

        return filteredProducts;
    }


    // Yeni product oluşturan HTTP POST request
    @PostMapping("api/products")
    public ResponseEntity<ProductID> createProduct(@RequestBody Product product) {
        System.out.println(product);
        ProductID result = new ProductID(UUID.randomUUID().toString());
        //ProductRepository e kaydedilir
        productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }


 //Belirli bir ürünü id'ye göre getiren HTTP GET isteğine yanıt verir.
    @GetMapping("api/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
//Mevcut ise Repository'e kayıt ve 200 OK degilse 404 NOT FOUND
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



//id ve guncellenen product ile Product Update etme
    @PutMapping("api/products/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Optional<Product> existingProduct = productService.findById(id);
//mevcut ise tüm variableleri güncelle 200OK degil ise 404 NOT FOUND
        if (existingProduct.isPresent()) {
            Product productToUpdate = existingProduct.get();
            productToUpdate.setProductName(updatedProduct.getProductName());
            productToUpdate.setProductCategoryID(updatedProduct.getProductCategoryID());
            productToUpdate.setProductPrice(updatedProduct.getProductPrice());
            productToUpdate.setProductColor(updatedProduct.getProductColor());
            productToUpdate.setProductStock(updatedProduct.getProductStock());

            productService.save(productToUpdate);

            return ResponseEntity.ok(productToUpdate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Id ile delete request atarak service ve repository üzerinden product silme
    @DeleteMapping("api/products/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        Optional<Product> productToDelete = productService.findById(id);
//Product mevcut ise 200 OK degil ise 404 NOT FOUND
        if (productToDelete.isPresent()) {
            List<SoldProduct> soldProducts = soldProductService.findByProductId(id);
            //Add an if statement to check if this product has been sold
            int i = 0;
            for (SoldProduct soldProduct : soldProducts) {
                soldProductService.delete(soldProducts.get(i));
                i++;
            }



            productService.delete(productToDelete.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}





