package com.tecsup.app.micro.product.repository;

import com.tecsup.app.micro.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
