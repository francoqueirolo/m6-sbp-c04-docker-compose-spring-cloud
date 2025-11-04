package com.tecsup.app.micro.product.controller;

import com.tecsup.app.micro.product.dto.Product;
import com.tecsup.app.micro.product.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getUserById(@PathVariable Long id) {
        log.info("REST request to get user by id: {}", id);
        return ResponseEntity.ok(productService.getUserById(id));
    }

}
