package com.jason.springbootjpa.repository;

import com.jason.springbootjpa.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository <ShoppingCartEntity, Long> {
    Optional<ShoppingCartEntity> findByBuyerId(Long buyerId);
}
