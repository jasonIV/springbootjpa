package com.jason.springbootjpa.controller;

import com.jason.springbootjpa.controller.request.model.ProductRequest;
import com.jason.springbootjpa.controller.response.model.ProductListResponse;
import com.jason.springbootjpa.controller.response.model.Response;
import com.jason.springbootjpa.entity.CompanyEntity;
import com.jason.springbootjpa.entity.ProductCategoryEntity;
import com.jason.springbootjpa.entity.ProductEntity;
import com.jason.springbootjpa.entity.enums.Status;
import com.jason.springbootjpa.repository.CompanyRepository;
import com.jason.springbootjpa.repository.ProductCategoryRepository;
import com.jason.springbootjpa.repository.ProductRepository;
import javassist.NotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/product")
@NoArgsConstructor
public class ProductController {

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProductRepository productRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    @ResponseBody
    public ResponseEntity<?> newProduct(@RequestBody ProductRequest request) {
       ProductEntity product = new ProductEntity();
       ProductCategoryEntity category = categoryRepository.findById(request.getCategoryId()).get();
       CompanyEntity company = companyRepository.findById(request.getCompanyId()).get();
       product.setProduct(request.getProduct());
       product.setDescription(request.getDescription());
       product.setBasePrice(request.getBasePrice());
       product.setUnitsInStock(request.getUnitsInStock());
       product.setProductCategoryEntity(category);
       product.setCompanyEntity(company);
       product.setDefaultData();
       productRepository.save(product);
       return ResponseEntity.ok(new Response("Product Created."));
    }

    @GetMapping("/getAllProductsWithCategory")
    @ResponseBody
    public List<ProductListResponse> getAllProductsWithCategory() {
        List<ProductListResponse> responses = new ArrayList<>();
        Iterator categoryIterator = categoryRepository.findAll().iterator();
        while(categoryIterator.hasNext()) {
            ProductCategoryEntity categoryEntity = (ProductCategoryEntity)categoryIterator.next();
            ProductListResponse response = new ProductListResponse();
            response.setCategoryName(categoryEntity.getCategory());
            response.setData(categoryEntity.getProductList());
            responses.add(response);
        }
        return responses;
    }

    @GetMapping("/getAllProductsWithStatus")
    @ResponseBody
    public List<ProductEntity> getAllProductsWithStatus(@RequestParam("status") Status status) {
        List<ProductEntity> data = productRepository.findByStatus(status);
        return data;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/updateStatus")
    @ResponseBody
    public ProductEntity updateState(@RequestParam("product_id") Long productId, @RequestParam("status") Status status) {
        ProductEntity entity = productRepository.getOne(productId);
        entity.setStatus(status);
        entity.setDefaultData();
        return (ProductEntity)this.productRepository.save(entity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit")
    @ResponseBody
    public ProductEntity editProduct(@RequestParam("product_id") Long productId, @RequestBody ProductRequest request) throws NotFoundException {
        ProductEntity entity = productRepository.getOne(productId);
        entity.setProduct(request.getProduct());
        entity.setDescription(request.getDescription());
        entity.setBasePrice(request.getBasePrice());
        entity.setUnitsInStock(request.getUnitsInStock());
        entity.setCategoryId(request.getCategoryId());
        entity.setCompanyId(request.getCompanyId());
        entity.setDefaultData();
        return (ProductEntity)this.productRepository.save(entity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    @ResponseBody
    public ProductEntity deleteProduct(@RequestParam("product_id") Long productId) throws NotFoundException {
        ProductEntity entity = productRepository.getOne(productId);
        entity.setStatus(Status.O);
        entity.setUnitsInStock(0);
        entity.setDefaultData();
        return (ProductEntity)this.productRepository.save(entity);
    }


}
