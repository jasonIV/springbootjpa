package com.jason.springbootjpa.controller.request.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderRequest {
    private List<OrderItemRequest> items;
}
