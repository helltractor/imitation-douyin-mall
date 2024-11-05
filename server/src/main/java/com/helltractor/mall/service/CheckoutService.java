package com.helltractor.mall.service;

import com.helltractor.mall.proto.cart.CartServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CheckoutService extends CartServiceGrpc.CartServiceImplBase {
}
