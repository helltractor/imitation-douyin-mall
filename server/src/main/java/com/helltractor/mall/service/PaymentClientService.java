package com.helltractor.mall.service;

import com.helltractor.mall.proto.payment.ChargeReq;
import com.helltractor.mall.proto.payment.ChargeResp;
import com.helltractor.mall.proto.payment.PaymentServiceGrpc.PaymentServiceBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class PaymentClientService {
    
    @GrpcClient("imitation-douyin-mall")
    private PaymentServiceBlockingStub paymentServiceBlockingStub;
    
    public ChargeResp charge(ChargeReq request) {
        return paymentServiceBlockingStub.charge(request);
    }
    
}