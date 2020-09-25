package com.jason.springbootjpa.repository;

import com.jason.springbootjpa.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    Optional<AdminEntity> findByUsername(String name);
}
