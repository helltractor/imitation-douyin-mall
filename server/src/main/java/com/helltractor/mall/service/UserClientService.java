package com.helltractor.mall.service;

import com.helltractor.mall.proto.user.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class UserClientService {
    
    @GrpcClient("imitation-douyin-mall")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;
    
    public RegisterResp register(RegisterReq request) {
        return userServiceBlockingStub.register(request);
    }
    
    public LoginResp login(LoginReq request) {
        return userServiceBlockingStub.login(request);
    }
    
}