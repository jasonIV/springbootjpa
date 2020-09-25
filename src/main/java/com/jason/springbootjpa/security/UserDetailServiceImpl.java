package com.jason.springbootjpa.security;

import com.jason.springbootjpa.entity.AdminEntity;
import com.jason.springbootjpa.entity.BuyerEntity;
import com.jason.springbootjpa.repository.AdminRepository;
import com.jason.springbootjpa.repository.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    public UserDetailServiceImpl(){

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] roleAndName  = username.split(":");
        String role = roleAndName[0];
        String name = roleAndName[1];
        if(role.equals("admin")) {
            Optional<AdminEntity> opAdmin = adminRepository.findByUsername(name);
            if (opAdmin.isEmpty()) throw new UsernameNotFoundException(name);
            AdminEntity admin = opAdmin.get();
            return new User(admin.getUsername(), admin.getPassword(), new ArrayList<>());
        } else {
            Optional<BuyerEntity> opBuyer = buyerRepository.findByUsername(name);
            BuyerEntity buyer = opBuyer.get();
            return new User(buyer.getUsername(), buyer.getPassword(), new ArrayList<>());
        }
    }

}