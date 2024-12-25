package com.helltractor.mall.service;

import com.helltractor.mall.entity.UserEntity;
import com.helltractor.mall.handler.TransferEntityHandler;
import com.helltractor.mall.mapper.UserServiceMapper;
import com.helltractor.mall.proto.user.*;
import com.helltractor.mall.util.PasswordUtil;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Slf4j
@GrpcService
public class UserServerService extends UserServiceGrpc.UserServiceImplBase {
    
    @Autowired
    RedisTemplate redisTemplate;
    
    @Autowired
    TransferEntityHandler handler;
    
    @Autowired
    UserServiceMapper userServiceMapper;
    
    @Override
    public void register(RegisterReq request, StreamObserver<RegisterResp> responseObserver) {
        try {
            UserEntity userEntity = userServiceMapper.searchUserByEmail(request.getEmail());
            if (userEntity != null && userEntity.getId() != 0) {
                log.error("Email already exists");
                throw new IllegalArgumentException("Email already exists");
            }
            
            userEntity = handler.transUserEntity(request);
            userServiceMapper.insert(userEntity);
            RegisterResp response = RegisterResp.newBuilder()
                    .setUserId(userEntity.getId())
                    .build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            log.error("Register failed");
            responseObserver.onError(e);
        } finally {
            responseObserver.onCompleted();
        }
    }
    
    @Override
    public void login(LoginReq request, StreamObserver<LoginResp> responseObserver) {
        try {
            UserEntity userEntity;
            Integer userId = (Integer) redisTemplate.opsForHash().get(request.getEmail(), request.getPassword());
            
            if (userId == null) {
                userEntity = userServiceMapper.searchUserByEmail(request.getEmail());
                if (userEntity == null || userEntity.getId() == 0) {
                    log.error("User not found");
                    throw new IllegalArgumentException("User not found");
                }
                
                boolean isPasswordValid = PasswordUtil.matches(request.getPassword(), userEntity.getPassword());
                if (!isPasswordValid) {
                    log.error("Password is incorrect");
                    throw new IllegalArgumentException("Password is incorrect");
                }
                
                redisTemplate.opsForHash().put(request.getEmail(), request.getPassword(), userEntity.getId());
                redisTemplate.expire(request.getEmail(), 1, TimeUnit.HOURS);
            } else {
                userEntity = UserEntity.builder().id(userId).build();
            }
            LoginResp response = LoginResp.newBuilder()
                    .setUserId(userEntity.getId())
                    .build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            log.error("Login failed");
            responseObserver.onError(e);
        } finally {
            responseObserver.onCompleted();
        }
    }
    
}