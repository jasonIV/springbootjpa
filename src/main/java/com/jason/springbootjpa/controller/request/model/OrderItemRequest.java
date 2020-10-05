package com.jason.springbootjpa.controller.request.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderItemRequest {
    private Long productId;
    private Integer quantity;
}
