package com.jason.springbootjpa.controller;

import com.jason.springbootjpa.entity.BuyerEntity;
import com.jason.springbootjpa.entity.CartProductEntity;
import com.jason.springbootjpa.entity.ProductEntity;
import com.jason.springbootjpa.entity.ShoppingCartEntity;
import com.jason.springbootjpa.repository.BuyerRepository;
import com.jason.springbootjpa.repository.CartProductRepository;
import com.jason.springbootjpa.repository.ProductRepository;
import com.jason.springbootjpa.repository.ShoppingCartRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
@NoArgsConstructor
public class ShoppingCartController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    @PostMapping("/add")
    public ResponseEntity<Object> addToShoppingCart(@RequestParam("product_id") Long productId, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        Optional<ProductEntity> opProduct = productRepository.findById(productId);
        if(opProduct.isEmpty()){
            throw new Exception("Product does not exist.");
        }
        ProductEntity product = opProduct.get();
        BuyerEntity buyer = buyerRepository.findByUsername(userDetails.getUsername()).get();
        Optional<ShoppingCartEntity> opCart = shoppingCartRepository.findByBuyerId(buyer.getId());
        ShoppingCartEntity shoppingCart;
        if(opCart.isEmpty()){
            ShoppingCartEntity cart = new ShoppingCartEntity();
            cart.setBuyer(buyer);
            cart.setDefaultData();
            shoppingCart = shoppingCartRepository.save(cart);
        } else {
            shoppingCart = opCart.get();
        }
        Optional<CartProductEntity> opCartProduct = cartProductRepository.findByProductIdAndShoppingCartId(productId, shoppingCart.getShoppingCartId());
        CartProductEntity cartProduct;
        CartProductEntity newCart;
        if (opCartProduct.isEmpty()) {
            newCart = new CartProductEntity();
            newCart.setProduct(product);
            newCart.setItemQuantity(1.00);
            newCart.setShoppingCart(shoppingCart);
            newCart.setDefaultData();
            cartProduct = cartProductRepository.save(newCart);
        } else {
            newCart = opCartProduct.get();
            if(newCart.getItemQuantity() > (double)product.getUnitsInStock()) {
                throw new Exception("Out of bound stock exception.");
            }
            newCart.setItemQuantity(newCart.getItemQuantity() + 1.00);
            newCart.setDefaultData();
            cartProduct = cartProductRepository.save(newCart);
        }
        return ResponseEntity.ok(cartProduct);
    }

    @GetMapping("/getCartData")
    public ResponseEntity<Object> getAllCartData(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
        Optional<BuyerEntity> opBuyer = buyerRepository.findByUsername(userDetails.getUsername());
        if(opBuyer.isEmpty()) {
            throw new Exception("User does not exist");
        } else {
            BuyerEntity buyer = opBuyer.get();
            Optional<ShoppingCartEntity> opShopping = shoppingCartRepository.findByBuyerId(buyer.getId());
            ShoppingCartEntity shoppingCart;
            if (opShopping.isEmpty()) {
                ShoppingCartEntity cart = new ShoppingCartEntity();
                cart.setBuyer(buyer);
                shoppingCart = shoppingCartRepository.save(cart);
            } else {
                shoppingCart = opShopping.get();
            }
            return ResponseEntity.ok(cartProductRepository.findByShoppingCartId(shoppingCart.getShoppingCartId()));
        }
    }
}
