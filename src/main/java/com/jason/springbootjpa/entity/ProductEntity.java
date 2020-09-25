package com.jason.springbootjpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity extends StatusEntity{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product")
    private String product;

    @Column(name = "description")
    private String description;

    @Column(name = "units_in_stock")
    private Integer unitsInStock;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private ProductCategoryEntity productCategoryEntity;

    @Column(
            name = "category_id",
            insertable = false,
            updatable = false
    )
    private Long categoryId;

    @ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplierEntity;

    @Column(
            name = "supplier_Id",
            insertable = false,
            updatable = false
    )
    private Long supplierId;

    @Column( name = "image")
    private String image;

    @Column( name = "base_price")
    private double basePrice;

}
