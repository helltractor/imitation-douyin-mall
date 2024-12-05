package com.helltractor.mall.service;

import com.helltractor.mall.proto.cart.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class CartClientService {
    
    @GrpcClient("imitation-douyin-mall")
    private CartServiceGrpc.CartServiceBlockingStub cartServiceBlockingStub;
    
    public AddItemResp addItem(AddItemReq request) {
        return cartServiceBlockingStub.addItem(request);
    }
    
    public GetCartResp getCart(GetCartReq request) {
        return cartServiceBlockingStub.getCart(request);
    }
    
    public EmptyCartResp emptyCart(EmptyCartReq request) {
        return cartServiceBlockingStub.emptyCart(request);
    }
    
}