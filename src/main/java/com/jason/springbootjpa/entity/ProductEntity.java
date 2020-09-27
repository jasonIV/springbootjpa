package com.jason.springbootjpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private ProductCategoryEntity productCategoryEntity;

    @Column(
            name = "category_id",
            insertable = false,
            updatable = false
    )
    private Long categoryId;

    @ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private CompanyEntity companyEntity;

    @Column(
            name = "company_Id",
            insertable = false,
            updatable = false
    )
    private Long companyId;

    @Column( name = "base_price")
    private double basePrice;

}
