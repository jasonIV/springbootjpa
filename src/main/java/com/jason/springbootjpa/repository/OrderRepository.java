package com.jason.springbootjpa.repository;

import com.jason.springbootjpa.entity.OrderEntity;
import com.jason.springbootjpa.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Page<OrderEntity> findByStatus(Status status, Pageable pageable);
    Page<OrderEntity> findByBuyerId(Long id, Pageable pageable);
}
