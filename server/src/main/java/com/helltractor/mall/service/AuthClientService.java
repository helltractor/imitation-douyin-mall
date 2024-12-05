package com.helltractor.mall.service;

import com.helltractor.mall.proto.auth.*;
import com.helltractor.mall.proto.auth.AuthServiceGrpc.AuthServiceBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class AuthClientService {
    
    @GrpcClient("imitation-douyin-mall")
    private AuthServiceBlockingStub authServiceBlockingStub;
    
    public DeliveryResp deliverToken(DeliverTokenReq request) {
        return authServiceBlockingStub.deliverTokenByRPC(request);
    }
    
    public VerifyResp verifyToken(VerifyTokenReq request) {
        return authServiceBlockingStub.verifyTokenByRPC(request);
    }
    
}