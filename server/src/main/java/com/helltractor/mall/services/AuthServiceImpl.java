package com.helltractor.mall.services;

import com.helltractor.mall.proto.auth.AuthServiceGrpc;
import com.helltractor.mall.proto.auth.DeliverTokenReq;
import com.helltractor.mall.proto.auth.DeliveryResp;
import com.helltractor.mall.proto.auth.VerifyResp;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AuthServiceImpl extends AuthServiceGrpc.AuthServiceImplBase {
    
    public DeliveryResp deliveryTokenByRPC(DeliverTokenReq request) {
        return DeliveryResp.newBuilder().build();
    }
    
    public VerifyResp verifyTokenByRPC(com.helltractor.mall.proto.auth.VerifyTokenReq request) {
        return VerifyResp.newBuilder().build();
    }
}
