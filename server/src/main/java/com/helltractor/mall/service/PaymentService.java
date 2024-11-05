package com.helltractor.mall.service;

import com.helltractor.mall.proto.payment.PaymentServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PaymentService extends PaymentServiceGrpc.PaymentServiceImplBase {
}
