package com.alten.products.product.service;

import com.alten.products.product.entity.ProductRequest;
import com.alten.products.product.entity.Product;
import com.alten.products.product.exception.ProductNotFoundException;
import com.alten.products.product.mapper.ProductMapper;
import com.alten.products.product.persistance.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
//replace with aop logging if necessary
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Product createProduct(ProductRequest productRequest) {
        log.debug("createProduct start productRequest = [{}]", productRequest);
        Product product = productMapper.createProductRequestToProduct(productRequest);
        Product result = productRepository.save(product);
        log.debug("createProduct returns [{}]", result);
        return result;
    }

    public Product findProduct(Long id) {
        log.debug("findProduct start id = [{}]", id);
        Product result = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        log.debug("findProduct returns [{}]", result);
        return result;
    }

    public List<Product> findAllProducts() {
        log.debug("findAllProducts start");
        List<Product> result = productRepository.findAll();
        log.debug("findAllProducts returns [{}]", result);
        return result;
    }

    public Product updateProduct(Long id, ProductRequest productRequest) {
        log.debug("updateProduct start id = [{}] and productRequest = [{}]", id, productRequest);
        if (productRepository.findById(id).isEmpty()) {
            throw new ProductNotFoundException(id);
        }
        Product product = productMapper.createProductRequestToProduct(productRequest);
        product.setId(id);
        Product result = productRepository.save(product);
        log.debug("updateProduct returns [{}]", result);
        return result;
    }

    public void deleteProduct(Long id) {
        log.debug("deleteProduct start id = [{}]", id);
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        () -> {
                            throw new ProductNotFoundException(id);
                        });
        log.debug("deleteProduct end");
    }
}
