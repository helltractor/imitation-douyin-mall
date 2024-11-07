package com.helltractor.mall.service;

import com.helltractor.mall.proto.checkout.CheckoutServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CheckoutService extends CheckoutServiceGrpc.CheckoutServiceImplBase {
}
