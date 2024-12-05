package com.helltractor.mall.handler;

import com.helltractor.mall.entity.*;
import com.helltractor.mall.proto.cart.AddItemReq;
import com.helltractor.mall.proto.cart.CartItem;
import com.helltractor.mall.proto.order.Address;
import com.helltractor.mall.proto.order.OrderItem;
import com.helltractor.mall.proto.order.PlaceOrderReq;
import com.helltractor.mall.proto.payment.ChargeReq;
import com.helltractor.mall.proto.user.RegisterReq;
import com.helltractor.mall.utils.PasswordUtil;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransferEntityHandler {
    
    public String generateUUID() {
        return UUID.randomUUID().toString();
    }
    
    public OrderAddressEntity transAddressEntity(PlaceOrderReq request) {
        Address address = request.getAddress();
        return OrderAddressEntity.builder()
                .userId(request.getUserId())
                .country(address.getCountry())
                .state(address.getState())
                .city(address.getCity())
                .streetAddress(address.getStreetAddress())
                .zipCode(address.getZipCode())
                .build();
    }
    
    public CartEntity transCartEntity(AddItemReq request) {
        return CartEntity.builder()
                .userId(request.getUserId())
                .productId(request.getItem().getProductId())
                .quantity(request.getItem().getQuantity())
                .build();
    }
    
    public OrderDetailEntity transOrderDetailEntity(OrderItem orderItem, String orderId) {
        CartItem cartItem = orderItem.getItem();
        return OrderDetailEntity.builder()
                .orderId(orderId)
                .productId(cartItem.getProductId())
                .quantity(cartItem.getQuantity())
                .cost(orderItem.getCost())
                .build();
    }
    
    public OrderEntity transOrdersEntity(PlaceOrderReq request) {
        return OrderEntity.builder()
                .orderId(generateUUID())
                .userId(request.getUserId())
                .email(request.getEmail())
                .userCurrency(request.getUserCurrency())
                .build();
    }
    
    public OrderEntity transOrdersEntity(PlaceOrderReq request, Integer addressBookId) {
        return OrderEntity.builder()
                .orderId(generateUUID())
                .addressBookId(addressBookId)
                .userId(request.getUserId())
                .email(request.getEmail())
                .userCurrency(request.getUserCurrency())
                .build();
    }
    
    public PaymentEntity transTransactionEntity(ChargeReq request) {
        return PaymentEntity.builder()
                .transactionId(generateUUID())
                .userId(request.getUserId())
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .build();
    }
    
    public UserEntity transUserEntity(RegisterReq request) {
        return UserEntity.builder()
                .password(PasswordUtil.encryptPassword(request.getPassword()))
                .email(request.getEmail())
                .confirmPassword(request.getConfirmPassword())
                .build();
    }
    
}