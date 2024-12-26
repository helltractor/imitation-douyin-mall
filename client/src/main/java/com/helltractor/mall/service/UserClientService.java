package com.helltractor.mall.service;

import com.helltractor.mall.proto.user.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class UserClientService {
    
    @GrpcClient("service-server")
    private UserServiceGrpc.UserServiceBlockingStub userServiceStub;
    
    public String register(String email, String password, String confirmPassword) {
        RegisterReq request = RegisterReq.newBuilder()
                .setEmail(email)
                .setPassword(password)
                .setConfirmPassword(confirmPassword)
                .build();
        RegisterResp response = this.userServiceStub.register(request);
        return String.valueOf(response.getUserId());
    }
    
    public String login(String email, String password) {
        LoginReq request = LoginReq.newBuilder().setEmail(email).setPassword(password).build();
        LoginResp response = this.userServiceStub.login(request);
        return String.valueOf(response.getUserId());
    }
    
}