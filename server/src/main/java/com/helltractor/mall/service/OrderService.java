package com.helltractor.mall.service;

import com.helltractor.mall.proto.order.OrderServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class OrderService extends OrderServiceGrpc.OrderServiceImplBase {
}
