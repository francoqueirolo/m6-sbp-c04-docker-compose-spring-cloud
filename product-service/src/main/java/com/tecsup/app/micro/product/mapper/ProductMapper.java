package com.tecsup.app.micro.product.mapper;

import com.tecsup.app.micro.product.client.User;
import com.tecsup.app.micro.product.dto.Product;
import com.tecsup.app.micro.product.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product domain);

    default Product toDomainWithUser(ProductEntity productEntity, User user){
        Product product = toDomain(productEntity);
        product.setCreatedByUser(user);
        return product;
    }
}
