package com.jason.springbootjpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity extends StatusEntity {

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;

   @Column(name = "total_amount")
   private Double totalAmount;

   @OneToMany(fetch = FetchType.EAGER, mappedBy = "orderEntity", cascade = CascadeType.ALL)
   private List<OrderItemEntity> orderItemEntities;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "buyer_id")
   private BuyerEntity buyerEntity;

   @Column(name = "buyer_id", insertable = false, updatable = false)
   private Long buyerId;

}
