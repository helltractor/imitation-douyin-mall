package com.helltractor.mall.services;

import com.helltractor.mall.proto.payment.ChargeReq;
import com.helltractor.mall.proto.payment.ChargeResp;
import com.helltractor.mall.proto.payment.PaymentServiceGrpc;

import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PaymentServiceImpl extends PaymentServiceGrpc.PaymentServiceImplBase {
    
    public ChargeResp charge(ChargeReq request) {
        return ChargeResp.newBuilder().build();
    }
}
