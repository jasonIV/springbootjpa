package com.jason.springbootjpa.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryEntity extends StatusEntity{

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "category")
    private String category;

    @JsonIgnore
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "productCategoryEntity",
            cascade = CascadeType.ALL
            )
    private List<ProductEntity> productList;

}
