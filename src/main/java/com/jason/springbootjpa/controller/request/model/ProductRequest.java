package com.jason.springbootjpa.controller.request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String product;
    private String description;
    private Integer unitsInStock;
    private Double basePrice;
    private Long categoryId;
    private Long companyId;
}
