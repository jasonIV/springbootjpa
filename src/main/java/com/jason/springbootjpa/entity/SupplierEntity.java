package com.jason.springbootjpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "supplier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierEntity extends StatusEntity{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @JsonIgnore
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "supplierEntity",
            cascade = CascadeType.ALL
    )
    private List<ProductEntity> productList;

}
