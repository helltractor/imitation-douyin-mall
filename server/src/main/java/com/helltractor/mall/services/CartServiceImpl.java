package com.helltractor.mall.services;

import com.helltractor.mall.proto.cart.AddItemReq;
import com.helltractor.mall.proto.cart.AddItemResp;
import com.helltractor.mall.proto.cart.CartServiceGrpc;
import com.helltractor.mall.proto.cart.EmptyCartReq;
import com.helltractor.mall.proto.cart.EmptyCartResp;
import com.helltractor.mall.proto.cart.GetCartReq;
import com.helltractor.mall.proto.cart.GetCartResp;

import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CartServiceImpl extends CartServiceGrpc.CartServiceImplBase {
    
    public AddItemResp addItem(AddItemReq request) {
        return AddItemResp.newBuilder().build();
    }
    
    public GetCartResp getCart(GetCartReq request) {
        return GetCartResp.newBuilder().build();
    }
    
    public EmptyCartResp emptyCart(EmptyCartReq request) {
        return EmptyCartResp.newBuilder().build();
    }
}
