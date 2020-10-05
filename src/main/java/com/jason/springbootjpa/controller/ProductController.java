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
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@NoArgsConstructor
@AllArgsConstructor
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
    public ResponseEntity<?> newProduct(@RequestBody ProductRequest request) throws Exception {
       ProductEntity product = new ProductEntity();
       Optional<ProductCategoryEntity> opCategory = categoryRepository.findById(request.getCategoryId());
       Optional<CompanyEntity> opCompany = companyRepository.findById(request.getCompanyId());
       if (opCategory.isEmpty()) throw new Exception("Category does not exist.");
       if (opCompany.isEmpty()) throw new Exception("Company does not exist.");
       ProductCategoryEntity category = opCategory.get();
       CompanyEntity company = opCompany.get();
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
    public List<ProductListResponse> getAllProductsWithCategory(Pageable pageable) {
        List<ProductListResponse> responses = new ArrayList<>();
        List<ProductCategoryEntity> categories = categoryRepository.findAll();
        for(ProductCategoryEntity element: categories) {
            ProductListResponse response = new ProductListResponse();
            Page<ProductEntity> products = productRepository.findByCategoryId(element.getId(), pageable);
            response.setCategoryName(element.getCategory());
            response.setData(products);
            responses.add(response);
        }
        return responses;
    }

    @GetMapping("/getAllProductsWithStatus")
    @ResponseBody
    public Page<ProductEntity> getAllProductsWithStatus(@RequestParam("status") Status status, Pageable pageable) {
        return productRepository.findByStatus(status, pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/updateStatus")
    @ResponseBody
    public ProductEntity updateState(@RequestParam("product_id") Long productId, @RequestParam("status") Status status) {
        ProductEntity entity = productRepository.getOne(productId);
        entity.setStatus(status);
        entity.setDefaultData();
        return this.productRepository.save(entity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit")
    @ResponseBody
    public ProductEntity editProduct(@RequestParam("product_id") Long productId, @RequestBody ProductRequest request) throws NotFoundException {
        Optional<ProductEntity> opEntity = productRepository.findById(productId);
        if(opEntity.isEmpty()) throw new NotFoundException("Product does not exist");
        ProductEntity entity = opEntity.get();
        entity.setProduct(request.getProduct());
        entity.setDescription(request.getDescription());
        entity.setBasePrice(request.getBasePrice());
        entity.setUnitsInStock(request.getUnitsInStock());
        entity.setCategoryId(request.getCategoryId());
        entity.setCompanyId(request.getCompanyId());
        entity.setDefaultData();
        return this.productRepository.save(entity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    @ResponseBody
    public ProductEntity deleteProduct(@RequestParam("product_id") Long productId) throws NotFoundException {
        Optional<ProductEntity> opEntity = productRepository.findById(productId);
        if(opEntity.isEmpty()) throw new NotFoundException("Product does not exist.");
        ProductEntity entity = opEntity.get();
        entity.setStatus(Status.outOfStock);
        entity.setUnitsInStock(0);
        entity.setDefaultData();
        return this.productRepository.save(entity);
    }

}
