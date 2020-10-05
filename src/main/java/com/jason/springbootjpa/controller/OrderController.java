package com.jason.springbootjpa.controller;

import com.jason.springbootjpa.controller.request.model.OrderItemRequest;
import com.jason.springbootjpa.controller.request.model.OrderRequest;
import com.jason.springbootjpa.controller.response.model.Response;
import com.jason.springbootjpa.entity.BuyerEntity;
import com.jason.springbootjpa.entity.OrderEntity;
import com.jason.springbootjpa.entity.OrderItemEntity;
import com.jason.springbootjpa.entity.ProductEntity;
import com.jason.springbootjpa.entity.enums.Status;
import com.jason.springbootjpa.repository.BuyerRepository;
import com.jason.springbootjpa.repository.OrderItemRepository;
import com.jason.springbootjpa.repository.OrderRepository;
import com.jason.springbootjpa.repository.ProductRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@NoArgsConstructor
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @PreAuthorize("hasRole('BUYER')")
    @PostMapping("/makeOrder")
    @ResponseBody
    public ResponseEntity<?> makeOrder(@RequestBody OrderRequest request, @AuthenticationPrincipal UserDetails user) throws Exception{
        List<OrderItemRequest> products = request.getItems();
        Optional<BuyerEntity> opBuyer = buyerRepository.findByUsername(user.getUsername());
        if(opBuyer.isEmpty()) throw new Exception("User does not exist.");
        BuyerEntity buyer = opBuyer.get();
        OrderEntity order = new OrderEntity();
        order.setBuyerEntity(buyer);
        order.setStatus(Status.pending);
        order.setDefaultData();
        orderRepository.save(order);
        double totalAmount = 0.0;
        for(OrderItemRequest element: products) {
            Optional<ProductEntity> opProduct = productRepository.findById(element.getProductId());
            if(opProduct.isEmpty()) throw new Exception("Product does not exist");
            ProductEntity product = opProduct.get();
            if(product.getUnitsInStock() < element.getQuantity()) throw new Exception("Product out of stock.");
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrderEntity(order);
            orderItem.setProductEntity(product);
            orderItem.setQuantity(element.getQuantity());
            orderItem.setPrice(product.getBasePrice());
            orderItem.setSubTotal(orderItem.getPrice()*orderItem.getQuantity());
            totalAmount = totalAmount + orderItem.getSubTotal();
            product.setUnitsInStock(product.getUnitsInStock() - element.getQuantity());
            product.setDefaultData();
            orderItem.setDefaultData();
            productRepository.save(product);
            orderItemRepository.save(orderItem);
        }
        order.setTotalAmount(totalAmount);
        order.setDefaultData();
        orderRepository.save(order);
        return ResponseEntity.ok(new Response("Order created successfully."));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllOrder")
    public Page<OrderEntity> getAllOrder(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @GetMapping("/getOrderByStatus")
    public Page<OrderEntity> getOrderByStatus(@RequestParam("status") Status status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable);
    }

    @GetMapping("/getOrderById")
    public OrderEntity getOrderById(@RequestParam("id") Long id) throws NotFoundException {
        Optional<OrderEntity> opOrder = orderRepository.findById(id);
        if(opOrder.isEmpty()) throw new NotFoundException("Order does not exist.");
        return opOrder.get();
    }

    @GetMapping("/getOrderByBuyerId")
    public Page<OrderEntity> getOrderByBuyerId(@RequestParam("id") Long id, Pageable pageable) {
        return orderRepository.findByBuyerId(id, pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateOrderStatus")
    public OrderEntity updateOrderStatus(@RequestParam("id") Long id, @RequestParam("status") Status status) throws NotFoundException {
       Optional<OrderEntity> opOrder = orderRepository.findById(id);
       if(opOrder.isEmpty()) throw new NotFoundException("Order does not exist");
       OrderEntity order = opOrder.get();
       order.setStatus(status);
       order.setDefaultData();
       return orderRepository.save(order);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteOrderById")
    public ResponseEntity<?> deleteOrderById(@RequestParam("id") Long id) throws NotFoundException {
        Optional<OrderEntity> opOrder = orderRepository.findById(id);
        if(opOrder.isEmpty()) throw new NotFoundException("Order does not exist.");
        OrderEntity order = opOrder.get();
        Iterable<OrderItemEntity> orderItems = order.getOrderItemEntities();
        orderItemRepository.deleteAll(orderItems);
        orderRepository.deleteById(order.getId());
        return ResponseEntity.ok(new Response("Order successfully deleted."));
    }

}