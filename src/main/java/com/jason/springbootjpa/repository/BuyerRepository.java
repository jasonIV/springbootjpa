package com.jason.springbootjpa.repository;

import com.jason.springbootjpa.entity.BuyerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BuyerRepository extends JpaRepository<BuyerEntity, Long>, CrudRepository<BuyerEntity, Long> {
    Optional<BuyerEntity> findByUsername(String username);
}
