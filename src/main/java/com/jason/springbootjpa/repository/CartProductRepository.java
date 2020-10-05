package com.jason.springbootjpa.repository;

import com.jason.springbootjpa.entity.CartProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartProductRepository extends JpaRepository <CartProductEntity, Long> {

    Optional<CartProductEntity> findByProductIdAndShoppingCartId(Long productId, Long shoppingCartId);

    List<CartProductEntity> findByShoppingCartId(Long shoppingCartid);

}
