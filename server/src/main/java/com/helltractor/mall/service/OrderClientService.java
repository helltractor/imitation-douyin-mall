package com.helltractor.mall.service;

import com.helltractor.mall.proto.order.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class OrderClientService {
    
    @GrpcClient("imitation-douyin-mall")
    private OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub;
    
    public PlaceOrderResp placeOrder(PlaceOrderReq request) {
        return orderServiceBlockingStub.placeOrder(request);
    }
    
    public ListOrderResp listOrder(ListOrderReq request) {
        return orderServiceBlockingStub.listOrder(request);
    }
    
    public MarkOrderPaidResp markOrderPaid(MarkOrderPaidReq request) {
        return orderServiceBlockingStub.markOrderPaid(request);
    }
    
}