package com.jason.springbootjpa.repository;

import com.jason.springbootjpa.entity.ProductCategoryEntity;
import com.jason.springbootjpa.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {

}
