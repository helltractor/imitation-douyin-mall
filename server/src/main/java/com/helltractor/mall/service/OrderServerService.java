package com.helltractor.mall.service;

import com.helltractor.mall.entity.OrderAddressEntity;
import com.helltractor.mall.entity.OrderDetailEntity;
import com.helltractor.mall.entity.OrderEntity;
import com.helltractor.mall.handler.TransferEntityHandler;
import com.helltractor.mall.mapper.OrderServiceMapper;
import com.helltractor.mall.proto.order.*;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@GrpcService
public class OrderServerService extends OrderServiceGrpc.OrderServiceImplBase {
    
    @Autowired
    TransferEntityHandler handler;
    
    @Autowired
    OrderServiceMapper orderServiceMapper;
    
    public void placeOrder(PlaceOrderReq request, StreamObserver<PlaceOrderResp> responseObserver) {
        try {
            OrderEntity order = null;
            if (request.hasAddress()) {
                OrderAddressEntity orderAddressEntity = handler.transAddressEntity(request);
                orderServiceMapper.insertAddress(orderAddressEntity);
                order = handler.transOrdersEntity(request, orderAddressEntity.getId());
            } else {
                // TODO: add default address_book_id
                order = handler.transOrdersEntity(request);
            }
            
            orderServiceMapper.insertOrders(order);
            
            List<OrderItem> orderItems = request.getOrderItemsList();
            for (OrderItem orderItem : orderItems) {
                OrderDetailEntity orderDetailEntity = handler.transOrderDetailEntity(orderItem, order.getOrderId());
                orderServiceMapper.insertOrderItem(orderDetailEntity);
            }
            
            OrderResult orderResult = OrderResult.newBuilder().setOrderId(order.getOrderId()).build();
            PlaceOrderResp response = PlaceOrderResp.newBuilder().setOrder(orderResult).build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            log.error("Place order failed", e);
           responseObserver.onError(e);
        }
        
        responseObserver.onCompleted();
    }
    
    public void listOrder(ListOrderReq request, StreamObserver<ListOrderResp> responseObserver) {
        try {
            List<Order> orders = orderServiceMapper.searchOrder(request.getUserId());
            ListOrderResp response = ListOrderResp.newBuilder().addAllOrders(orders).build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            log.error("List order failed", e);
            responseObserver.onError(e);
        }

        responseObserver.onCompleted();
    }
    
    public void markOrderPaid(MarkOrderPaidReq request, StreamObserver<MarkOrderPaidResp> responseObserver) {
        try {
            orderServiceMapper.updatePaidStatus(request.getUserId(), request.getOrderId());
            MarkOrderPaidResp response = MarkOrderPaidResp.newBuilder().build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            log.error("Mark order paid failed", e);
            responseObserver.onError(e);
        }
        
        responseObserver.onCompleted();
    }
    
}