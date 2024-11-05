package com.helltractor.mall.service;

import com.helltractor.mall.proto.user.UserServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {
}
