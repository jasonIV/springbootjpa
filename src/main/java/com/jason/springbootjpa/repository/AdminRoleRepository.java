package com.jason.springbootjpa.repository;

import com.jason.springbootjpa.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRoleRepository extends JpaRepository<AdminRole, Long> {
}
