package com.helltractor.mall.service;

import com.helltractor.mall.constant.JwtClaimConstant;
import com.helltractor.mall.proto.auth.AuthServiceGrpc;
import com.helltractor.mall.proto.auth.DeliverTokenReq;
import com.helltractor.mall.proto.auth.DeliveryResp;
import com.helltractor.mall.proto.auth.VerifyResp;
import com.helltractor.mall.proto.auth.VerifyTokenReq;

import com.helltractor.mall.utils.JwtUtil;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@GrpcService
public class AuthServerService extends AuthServiceGrpc.AuthServiceImplBase {
    
    @Override
    public void deliverTokenByRPC(DeliverTokenReq request, StreamObserver<DeliveryResp> responseObserver) {
        try {
            Map claims = new HashMap<>();
            claims.put(JwtClaimConstant.USER_ID, request.getUserId());
            String token = JwtUtil.createJWT(claims);
            DeliveryResp response = DeliveryResp.newBuilder()
                    .setToken(token)
                    .build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            log.error("Deliver token failed", e);
            responseObserver.onError(e);
        }
        
        responseObserver.onCompleted();
    }
    
    @Override
    public void verifyTokenByRPC(VerifyTokenReq request, StreamObserver<VerifyResp> responseObserver) {
        try {
            boolean isValid = JwtUtil.verifyJWTExp(request.getToken());
            VerifyResp response = VerifyResp.newBuilder()
                    .setRes(isValid)
                    .build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            log.error("Verify token failed", e);
            responseObserver.onError(e);
        }
        
        responseObserver.onCompleted();
    }
    
}