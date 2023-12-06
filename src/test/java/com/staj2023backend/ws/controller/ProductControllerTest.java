package com.staj2023backend.ws.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.staj2023backend.ws.model.Product;
import com.staj2023backend.ws.model.ProductID;
import com.staj2023backend.ws.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    Product RECORD_1 = new Product(1L, "Product 1", 1L, BigDecimal.valueOf(100), "Red", 10L);
    Product RECORD_2 = new Product(2L, "Product 2", 1L, BigDecimal.valueOf(200), "Blue", 20L);
    Product RECORD_3 = new Product(3L, "Product 3", 2L, BigDecimal.valueOf(150), "Green", 15L);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        List<Product> products = new ArrayList<>(List.of(RECORD_1, RECORD_2, RECORD_3));
        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(products.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(RECORD_1.getId()))
                .andExpect(jsonPath("$[0].productName").value(RECORD_1.getProductName()))
                .andExpect(jsonPath("$[1].id").value(RECORD_2.getId()))
                .andExpect(jsonPath("$[1].productName").value(RECORD_2.getProductName()))
                .andExpect(jsonPath("$[2].id").value(RECORD_3.getId()))
                .andExpect(jsonPath("$[2].productName").value(RECORD_3.getProductName()));
    }

    @Test
    public void testGetProductByCategory() throws Exception {
        List<Product> products = new ArrayList<>(List.of(RECORD_1, RECORD_2, RECORD_3));
        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/category/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(RECORD_1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(RECORD_2.getId()));
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product newProduct = new Product(null, "New Product", 3L, BigDecimal.valueOf(250), "Yellow", 5L);
        UUID randomUUID = UUID.randomUUID();
        ProductID result = new ProductID(randomUUID.toString());

        doNothing().when(productService).save(any(Product.class));

        String productJson = objectMapper.writeValueAsString(newProduct);

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty()); // Check if the JSON response is not empty
    }





    @Test
    public void testGetProductById_existingProduct_returnsProduct() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.of(RECORD_1));

        mockMvc.perform(get("/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(RECORD_1.getId()))
                .andExpect(jsonPath("$.productName").value(RECORD_1.getProductName()));
    }

    @Test
    public void testGetProductById_nonExistingProduct_returnsNotFound() throws Exception {
        when(productService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/product/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateProductById_existingProduct_returnsUpdatedProduct() throws Exception {
        Product updatedProduct = new Product(1L, "Updated Product", 1L, BigDecimal.valueOf(120), "Purple", 8L);

        when(productService.findById(1L)).thenReturn(Optional.of(RECORD_1));
        doNothing().when(productService).save(any(Product.class));

        String productJson = objectMapper.writeValueAsString(updatedProduct);

        mockMvc.perform(patch("/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(updatedProduct.getId()))
                .andExpect(jsonPath("$.productName").value(updatedProduct.getProductName()))
                .andExpect(jsonPath("$.productPrice").value(updatedProduct.getProductPrice()));
    }

    @Test
    public void testDeleteProductById_existingProduct_returnsOk() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.of(RECORD_1));
        doNothing().when(productService).delete(any(Product.class));

        mockMvc.perform(delete("/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteProductById_nonExistingProduct_returnsNotFound() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
