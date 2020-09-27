package com.jason.springbootjpa.repository;

import com.jason.springbootjpa.entity.ProductEntity;
import com.jason.springbootjpa.entity.StatusEntity;
import com.jason.springbootjpa.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByStatus(Status status);
}
