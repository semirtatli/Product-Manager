package com.staj2023backend.ws.service;

import com.staj2023backend.ws.model.Product;
import com.staj2023backend.ws.repository.ProductRepository;
import com.staj2023backend.ws.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private final Product RECORD_1 = new Product(1L, "Product 1", 1L, BigDecimal.valueOf(100), "Red", 10L);
    private final Product RECORD_2 = new Product(2L, "Product 2", 2L, BigDecimal.valueOf(200), "Blue", 20L);
    private final Product RECORD_3 = new Product(3L, "Product 3", 1L, BigDecimal.valueOf(150), "Green", 15L);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveProduct() {
        productService.save(RECORD_1);

        verify(productRepository, times(1)).save(RECORD_1);
    }

    @Test
    public void testFindAllProducts() {
        List<Product> products = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.findAll();

        assertEquals(products.size(), result.size());
        assertEquals(RECORD_1.getProductName(), result.get(0).getProductName());
        assertEquals(RECORD_2.getProductName(), result.get(1).getProductName());
        assertEquals(RECORD_3.getProductName(), result.get(2).getProductName());
    }

    @Test
    public void testFindProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(RECORD_1));

        Optional<Product> result = productService.findById(1L);

        assertEquals(RECORD_1.getProductName(), result.get().getProductName());
    }

    @Test
    public void testFindNonExistingProductById() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Product> result = productService.findById(99L);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testDeleteExistingProduct() {
        // Create a sample existing product
        Product existingProduct = new Product(1L, "Sample Product", 1L, BigDecimal.valueOf(100), "Red", 10L);

        // Stub the behavior of findById using lenient()
        lenient().when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(existingProduct));

        // Call the delete method
        productService.delete(existingProduct);

        // Verify that productRepository.delete was called with the correct product
        verify(productRepository, times(1)).delete(existingProduct);
    }

}
