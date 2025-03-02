package com.product_manager.ws.controller;

import com.product_manager.ws.model.Product;
import com.product_manager.ws.model.SoldProduct;
import com.product_manager.ws.service.ProductService;
import com.product_manager.ws.model.Selling;
import com.product_manager.ws.service.SellingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// SellingController sınıfı, satış işlemleriyle ilgili HTTP requestlerine yanıt veren REST API işlemlerini gerçekleştirir
@RestController
public class SellingController {
    // SellingService ve ProductService sınıflarını otomatik olarak enjekte eder.
    @Autowired
    SellingService sellingService;
    @Autowired
    private ProductService productService;
    public SellingController(SellingService sellingService, ProductService productService) {
        this.sellingService = sellingService;
        this.productService = productService;
    }
    // Yeni bir satış kaydı oluşturan HTTP POST isteğine yanıt verir.
//    @PostMapping("api/orders")
//    public ResponseEntity<Selling> createOrder(@RequestBody Selling selling) {
//        System.out.println(selling);
//        ProductID result = new ProductID(selling.toString());
//        // Verilen ürün ID'si ile mevcut ürünü bulur.
//        Optional<Product> existingProduct = productService.findById(selling.getProductID());
//
//        if (existingProduct.isPresent()) {
//            Product productToUpdate = existingProduct.get();
//// Ürün stok miktarını satılan ürün adedine göre düşürür.
//            productToUpdate.setProductStock(productToUpdate.getProductStock()-selling.getNumberOfProduct());
////Product guncelleme, stock durumu degisimi
//            productService.save(productToUpdate);
//        }
//// Satıs kaydı ve 200 OK
//        sellingService.save(selling);
//        return ResponseEntity.ok(selling);
//    }




    @PostMapping("api/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Selling> createOrder(@RequestBody Selling selling) {
        System.out.println(selling);

        // Iterate through the list of sold products in the Selling object
        for (SoldProduct soldProduct : selling.getSoldProducts()) {
            // Find the existing product by its ID
            Optional<Product> existingProduct = productService.findById(soldProduct.getProduct().getId());

            if (existingProduct.isPresent()) {
                Product productToUpdate = existingProduct.get();
                // Update the product stock based on the quantity sold
                productToUpdate.setProductStock(productToUpdate.getProductStock() - soldProduct.getNumberOfProduct());
                // Save the updated product
                productService.save(productToUpdate);
            }
        }

        // Save the order (Selling entity)
        sellingService.save(selling);

        return ResponseEntity.ok(selling);
    }





    // Tüm selling kayıtlarını listeleyen HTTP GET request
    @GetMapping("api/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Selling> getAllSelling() {
        return sellingService.findAll();
    }



}
