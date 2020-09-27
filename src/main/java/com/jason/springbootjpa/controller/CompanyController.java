package com.jason.springbootjpa.controller;

import com.jason.springbootjpa.controller.request.model.CompanyRegisterRequest;
import com.jason.springbootjpa.entity.CompanyEntity;
import com.jason.springbootjpa.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    public ResponseEntity<CompanyEntity> newCompany(@RequestBody CompanyRegisterRequest request) throws Exception{
        CompanyEntity company = new CompanyEntity();
        company.setCompany(request.getCompany());
        company.setAddress(request.getAddress());
        company.setPhone(request.getPhone());
        company.setEmail(request.getEmail());
        company.setDefaultData();
        CompanyEntity response = companyRepository.save(company);
        return ResponseEntity.ok(response);
    }
}
