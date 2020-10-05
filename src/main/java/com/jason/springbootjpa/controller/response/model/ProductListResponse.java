package com.jason.springbootjpa.controller.response.model;

import com.jason.springbootjpa.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponse {

    private String categoryName;
    private Page<ProductEntity> data;

}
