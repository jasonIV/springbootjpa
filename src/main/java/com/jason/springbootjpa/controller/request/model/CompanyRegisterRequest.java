package com.jason.springbootjpa.controller.request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRegisterRequest {

    private String company;
    private String address;
    private String phone;
    private String email;

}
