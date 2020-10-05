package com.jason.springbootjpa.repository;

import com.jason.springbootjpa.entity.ProductCategoryEntity;
import com.jason.springbootjpa.entity.ProductEntity;
import com.jason.springbootjpa.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findByStatus(Status status, Pageable pageable);

    Page<ProductEntity> findByCategoryId(Long id, Pageable pageable);
}
