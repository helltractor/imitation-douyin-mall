package com.helltractor.mall.service;

import com.helltractor.mall.config.ServiceTestConfiguration;
import com.helltractor.mall.entity.OrderAddressEntity;
import com.helltractor.mall.entity.OrderDetailEntity;
import com.helltractor.mall.entity.OrderEntity;
import com.helltractor.mall.handler.TransferEntityHandler;
import com.helltractor.mall.mapper.OrderServiceMapper;
import com.helltractor.mall.proto.order.*;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static com.helltractor.mall.constant.BaseParamConstant.*;
import static com.helltractor.mall.constant.ModelConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        "grpc.server.in-process-name=test",
        "grpc.server.port=-1",
        "grpc.client.serviceServer.address=in-process:test"
})
@SpringJUnitConfig(ServiceTestConfiguration.class)
@DirtiesContext
public class OrderServerServiceTest {
    
    @InjectMocks
    private OrderServerService orderServerService;
    
    @Autowired
    private TransferEntityHandler handler;
    
    @Mock
    private OrderServiceMapper orderServiceMapper;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.orderServerService.handler = handler;
    }
    
    @Test
    public void testPlaceOrder() {
        PlaceOrderReq placeOrderReq = PlaceOrderReq.newBuilder()
                .setUserId(USER_ID)
                .setUserCurrency(USER_CURRENCY_USD)
                .setAddress(ORDER_ADDRESS)
                .setEmail(EMAIL)
                .addOrderItems(ORDER_ITEM_ONE)
                .build();
        StreamRecorder<PlaceOrderResp> responseObserver = StreamRecorder.create();
        orderServerService.placeOrder(placeOrderReq, responseObserver);
        
        assertNull(responseObserver.getError());
        
        PlaceOrderResp response = responseObserver.getValues().get(0);
        assertNotNull(response.getOrder().getOrderId());
        
        verify(orderServiceMapper).insertAddress(any(OrderAddressEntity.class));
        verify(orderServiceMapper).insertOrderItem(any(OrderDetailEntity.class));
        verify(orderServiceMapper).insertOrders(any(OrderEntity.class));
    }
    
    @Test
    public void testListOrder() {
        List<Order> orders = List.of(ORDER);
        when(orderServiceMapper.searchOrder(USER_ID)).thenReturn(orders);
        
        ListOrderReq listOrderReq = ListOrderReq.newBuilder()
                .setUserId(USER_ID)
                .build();
        StreamRecorder<ListOrderResp> responseObserver = StreamRecorder.create();
        orderServerService.listOrder(listOrderReq, responseObserver);
        
        assertNull(responseObserver.getError());

        ListOrderResp response = responseObserver.getValues().get(0);
        assertEquals(orders, response.getOrdersList());
        
        verify(orderServiceMapper).searchOrder(USER_ID);
    }
    
    @Test
    public void testMarkOrderPaid() {
        MarkOrderPaidReq markOrderPaidReq = MarkOrderPaidReq.newBuilder()
                .setUserId(USER_ID)
                .setOrderId(ORDER_ID)
                .build();
        StreamRecorder<MarkOrderPaidResp> responseObserver = StreamRecorder.create();
        orderServerService.markOrderPaid(markOrderPaidReq, responseObserver);
        
        assertNull(responseObserver.getError());
        
        MarkOrderPaidResp response = responseObserver.getValues().get(0);
        assertEquals(MarkOrderPaidResp.newBuilder().build(), response);
        
        verify(orderServiceMapper).updatePaidStatus(USER_ID, ORDER_ID);
    }
    
}