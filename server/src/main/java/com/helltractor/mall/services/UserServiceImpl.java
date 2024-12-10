package com.helltractor.mall.services;

import com.helltractor.mall.proto.user.LoginReq;
import com.helltractor.mall.proto.user.RegisterReq;
import com.helltractor.mall.proto.user.UserServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    
    public RegisterReq register(RegisterReq request) {
        return RegisterReq.newBuilder().build();
    }
    
    public LoginReq login(LoginReq request) {
        return LoginReq.newBuilder().build();
    }
}
