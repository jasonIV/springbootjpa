package com.jason.springbootjpa.controller;

import com.jason.springbootjpa.controller.request.model.AuthenticationRequest;
import com.jason.springbootjpa.controller.request.model.BuyerRegisterRequest;
import com.jason.springbootjpa.controller.response.model.Response;
import com.jason.springbootjpa.entity.BuyerEntity;
import com.jason.springbootjpa.repository.BuyerRepository;
import com.jason.springbootjpa.security.JWTTokenProvider;
import com.jason.springbootjpa.security.UserDetailServiceImpl;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@NoArgsConstructor
@RequestMapping("/buyer")
public class BuyerController {

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerBuyer(@RequestBody BuyerRegisterRequest request) throws Exception{
       BuyerEntity buyer = new BuyerEntity();
       BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
       buyer.setUsername(request.getUsername());
       buyer.setPassword(encoder.encode(request.getPassword()));
       buyer.setPhone(request.getPhone());
       buyer.setEmail(request.getEmail());
       buyer.setDeliveryAddress(request.getDeliveryAddress());
       buyer.setShopName(request.getShopName());
       buyer.setCreatedBy(request.getUsername());
       buyer.setLastUpdateBy(request.getUsername());
       buyer.setCreatedDate(new Date());
       buyer.setLastUpdateDate(new Date());
       buyerRepository.save(buyer);
       return ResponseEntity.ok(new Response("Buyer account successfully registered."));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginBuyer(@RequestBody AuthenticationRequest request) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("buyer:" + request.getUsername(), request.getPassword()));
        UserDetails userDetails = userDetailService.loadUserByUsername("buyer:" + request.getUsername());
        String token = jwtTokenProvider.createToken(userDetails);
        return ResponseEntity.ok(new Response(token));
    }
}
