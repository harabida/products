package com.alten.products.product.service;

import com.alten.products.product.entity.Product;
import com.alten.products.product.entity.ProductRequest;
import com.alten.products.product.exception.ProductNotFoundException;
import com.alten.products.product.mapper.ProductMapper;
import com.alten.products.product.persistance.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct() {
        // Given
        ProductRequest productRequest = new ProductRequest("code", "name", "description", BigDecimal.TEN, 100, "IN_STOCK", "category", "image", 5);
        Product product = new Product();
        when(productMapper.createProductRequestToProduct(productRequest)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);

        // When
        Product result = productService.createProduct(productRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(product).isEqualTo(result);
        verify(productMapper).createProductRequestToProduct(productRequest);
        verify(productRepository).save(product);
    }

    @Test
    void findProduct() {
        // Given
        Long productId = 1L;
        Product expectedProduct = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));

        // When
        Product result = productService.findProduct(productId);

        // Then
        assertThat(result).isNotNull();
        assertThat(expectedProduct).isEqualTo(result);
        verify(productRepository).findById(productId);
    }

    @Test
    void findAllProducts() {
        // Given
        List<Product> expectedProducts = Collections.singletonList(new Product());
        when(productRepository.findAll()).thenReturn(expectedProducts);

        // When
        List<Product> result = productService.findAllProducts();

        // Then
        assertThat(result).isNotNull();
        assertThat(expectedProducts).isEqualTo(result);
        verify(productRepository).findAll();
    }

    @Test
    void updateProduct() {
        // Given
        Long productId = 1L;
        ProductRequest productRequest = new ProductRequest("code", "name", "description", BigDecimal.TEN, 100, "IN_STOCK", "category", "image", 5);
        Product existingProduct = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productMapper.createProductRequestToProduct(productRequest)).thenReturn(existingProduct);
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        // When
        Product result = productService.updateProduct(productId, productRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(existingProduct).isEqualTo(result);
        verify(productRepository).findById(productId);
        verify(productMapper).createProductRequestToProduct(productRequest);
        verify(productRepository).save(existingProduct);
    }

    @Test
    void deleteProduct() {
        // Given
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(new Product()));

        // When
        productService.deleteProduct(productId);

        // Then
        verify(productRepository).findById(productId);
        verify(productRepository).delete(any());
    }

    @Test
    void deleteProduct_ProductNotFound() {
        // Given
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(productId));

        // Then
        verify(productRepository).findById(productId);
        verify(productRepository, never()).delete(any());
    }
}
