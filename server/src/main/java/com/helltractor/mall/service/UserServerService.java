package com.helltractor.mall.service;

import com.helltractor.mall.entity.UserEntity;
import com.helltractor.mall.handler.TransferEntityHandler;
import com.helltractor.mall.mapper.UserServiceMapper;
import com.helltractor.mall.proto.user.*;
import com.helltractor.mall.utils.PasswordUtil;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@GrpcService
public class UserServerService extends UserServiceGrpc.UserServiceImplBase {
    
    @Autowired
    TransferEntityHandler handler;
    
    @Autowired
    UserServiceMapper userServiceMapper;
    
    @Override
    public void register(RegisterReq request, StreamObserver<RegisterResp> responseObserver) {
        try {
            UserEntity userEntity = handler.transUserEntity(request);
            userServiceMapper.insert(userEntity);
            if (userEntity == null || userEntity.getId() == 0) {
                log.error("Email already exists");
                throw new IllegalArgumentException("Email already exists");
            }
            
            RegisterResp response = RegisterResp.newBuilder()
                    .setUserId(userEntity.getId())
                    .build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            log.error("Register failed");
            responseObserver.onError(e);
        }
        
        responseObserver.onCompleted();
    }
    
    @Override
    public void login(LoginReq request, StreamObserver<LoginResp> responseObserver) {
        try {
            UserEntity user = userServiceMapper.searchUserByEmail(request.getEmail());
            if (user == null || user.getId() == 0) {
                log.error("User not found");
                throw new IllegalArgumentException("User not found");
            }
            
            boolean isPasswordValid = PasswordUtil.matches(request.getPassword(), user.getPassword());
            if (!isPasswordValid) {
                log.error("Password is incorrect");
                throw new IllegalArgumentException("Password is incorrect");
            }
            
            LoginResp response = LoginResp.newBuilder()
                    .setUserId(user.getId())
                    .build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            log.error("Login failed");
            responseObserver.onError(e);
        }
        
        responseObserver.onCompleted();
    }
    
}