package com.jason.springbootjpa.controller;

import com.jason.springbootjpa.controller.request.model.NewCategoryRequest;
import com.jason.springbootjpa.entity.ProductCategoryEntity;
import com.jason.springbootjpa.repository.ProductCategoryRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@NoArgsConstructor
public class ProductCategoryController {

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    public ProductCategoryEntity newCategory(@RequestBody NewCategoryRequest request, @AuthenticationPrincipal UserDetails userDetails){
        ProductCategoryEntity category = new ProductCategoryEntity();
        category.setDefaultData();
        category.setCategory(request.getCategory());
        return (ProductCategoryEntity) categoryRepository.save(category);
    }
}
