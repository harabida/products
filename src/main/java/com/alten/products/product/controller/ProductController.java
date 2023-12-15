package com.alten.products.product.controller;

import com.alten.products.product.entity.ProductRequest;
import com.alten.products.product.entity.Product;
import com.alten.products.product.exception.ProductNotFoundException;
import com.alten.products.product.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;


    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        log.debug("createProduct start productRequest = [{}]", productRequest);
        Product saved = productService.createProduct(productRequest);

        return ResponseEntity.created(URI.create("/v1/products/" + saved.getId())).build();
    }


    @GetMapping
    public ResponseEntity<List<Product>> findAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @GetMapping("/{id:[0-9]*}")
    public ResponseEntity<Product> findOneProduct(@PathVariable("id") @NotNull Long id) {
        return ResponseEntity.ok(productService.findProduct(id));
    }

    @DeleteMapping("/{id:[0-9]*}")
    public ResponseEntity<Object> deleteOneProduct(@PathVariable("id") @NotNull Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id:[0-9]*}")
    public ResponseEntity<Object> updateProduct(@PathVariable("id") @NotNull Long id, @RequestBody @Valid ProductRequest productRequest) {
        Product product = productService.updateProduct(id, productRequest);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFound(
            ProductNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

}
