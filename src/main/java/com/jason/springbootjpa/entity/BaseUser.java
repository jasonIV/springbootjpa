package com.jason.springbootjpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseUser extends StatusEntity{
    private static final long serialVersionUID = 7801663576677865267L;

    @Column(name = "username", length = 50, unique = true)
    private String username;

    @Column(name = "password")
    @JsonIgnore
    private String password;

}
