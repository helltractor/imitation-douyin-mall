package com.helltractor.mall.services;

import com.helltractor.mall.proto.checkout.CheckoutReq;
import com.helltractor.mall.proto.checkout.CheckoutResp;
import com.helltractor.mall.proto.checkout.CheckoutServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CheckoutServiceImpl extends CheckoutServiceGrpc.CheckoutServiceImplBase {
    
    public CheckoutResp checkout(CheckoutReq request) {
        return CheckoutResp.newBuilder().build();
    }
}
