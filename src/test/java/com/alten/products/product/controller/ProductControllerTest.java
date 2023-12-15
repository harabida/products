package com.alten.products.product.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.alten.products.product.entity.Product;
import com.alten.products.product.entity.ProductRequest;
import com.alten.products.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@WebMvcTest(ProductController.class)
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createProduct() throws Exception {
        // Given
        ProductRequest productRequest = new ProductRequest("code", "name", "description", BigDecimal.TEN, 100, "IN_STOCK", "category", "image", 5);
        Product savedProduct = new Product();
        when(productService.createProduct(any())).thenReturn(savedProduct);

        // When
        mockMvc.perform(post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated());

        // Then
        verify(productService).createProduct(productRequest);
    }

    @Test
    void findAllProducts() throws Exception {
        // Given
        List<Product> products = Collections.singletonList(new Product());
        when(productService.findAllProducts()).thenReturn(products);

        // When
        mockMvc.perform(get("/v1/products"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(products)));

        // Then
        verify(productService).findAllProducts();
    }

    @Test
    void findOneProduct() throws Exception {
        // Given
        Long productId = 1L;
        Product product = new Product();
        when(productService.findProduct(productId)).thenReturn(product);

        // When
        mockMvc.perform(get("/v1/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(product)));

        // Then
        verify(productService).findProduct(productId);
    }

    @Test
    void deleteOneProduct() throws Exception {
        // Given
        Long productId = 1L;

        // When
        mockMvc.perform(delete("/v1/products/{id}", productId))
                .andExpect(status().isOk());

        // Then
        verify(productService).deleteProduct(productId);
    }

    @Test
    void updateProduct() throws Exception {
        // Given
        Long productId = 1L;
        ProductRequest productRequest = new ProductRequest("code", "name", "description", BigDecimal.TEN, 100, "IN_STOCK", "category", "image", 5);
        Product updatedProduct = new Product();
        when(productService.updateProduct(productId, productRequest)).thenReturn(updatedProduct);

        // When
        mockMvc.perform(patch("/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isNoContent());

        // Then
        verify(productService).updateProduct(productId, productRequest);
    }
}
