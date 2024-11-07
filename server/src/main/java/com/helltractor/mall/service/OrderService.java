package com.helltractor.mall.service;

import com.helltractor.mall.proto.order.*;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class OrderService extends OrderServiceGrpc.OrderServiceImplBase {
    
    public PlaceOrderResp placeOrder(PlaceOrderReq request) {
        return PlaceOrderResp.newBuilder().build();
    }
    
    public ListOrderResp listOrder(ListOrderReq request) {
        return ListOrderResp.newBuilder().build();
    }
    
    public MarkOrderPaidResp markOrderPaid(MarkOrderPaidReq request) {
        return MarkOrderPaidResp.newBuilder().build();
    }
}
