package com.tecsup.app.micro.product.dto;

import com.tecsup.app.micro.product.client.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String category;
    //private Long createdBy;
    //
    private User createdByUser;

}