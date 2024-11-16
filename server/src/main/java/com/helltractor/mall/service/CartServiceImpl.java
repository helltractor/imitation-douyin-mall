package com.helltractor.mall.service;

import com.helltractor.mall.mapper.CartServiceMapper;
import com.helltractor.mall.proto.cart.*;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GrpcService
public class CartServiceImpl extends CartServiceGrpc.CartServiceImplBase {
    
    private final CartServiceMapper cartServiceMapper;
    
    @Autowired
    public CartServiceImpl(CartServiceMapper cartServiceMapper) {
        this.cartServiceMapper = cartServiceMapper;
    }
    
    public void addItem(AddItemReq request, StreamObserver<AddItemResp> responseObserver) {
        int userId = request.getUserId();
        CartItem cartItem = request.getItem();
        cartServiceMapper.insert(userId, cartItem);
        AddItemResp resp = AddItemResp.newBuilder().build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
    
    @Override
    public void getCart(GetCartReq request, StreamObserver<GetCartResp> responseObserver) {
        int userId = request.getUserId();
        List<CartItem> cartItems = cartServiceMapper.search(userId);
        Cart cart = Cart.newBuilder().addAllItems(cartItems).build();
        GetCartResp resp = GetCartResp.newBuilder().setCart(cart).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
    
    @Override
    public void emptyCart(EmptyCartReq request, StreamObserver<EmptyCartResp> responseObserver) {
        int userId = request.getUserId();
        cartServiceMapper.delete(userId);
        EmptyCartResp resp = EmptyCartResp.newBuilder().build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
    
}