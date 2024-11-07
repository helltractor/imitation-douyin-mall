package com.helltractor.mall.service;

import com.helltractor.mall.proto.user.LoginReq;
import com.helltractor.mall.proto.user.RegisterReq;
import com.helltractor.mall.proto.user.UserServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {
    
    public RegisterReq register(RegisterReq request) {
        return RegisterReq.newBuilder().build();
    }
    
    public LoginReq login(LoginReq request) {
        return LoginReq.newBuilder().build();
    }
}
