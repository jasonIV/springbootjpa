package com.jason.springbootjpa.controller.request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyerRegisterRequest extends AuthenticationRequest{
    private String email;
    private String phone;
    private String deliveryAddress;
    private String shopName;
}
