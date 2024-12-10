package com.helltractor.mall.service;

import com.helltractor.mall.config.ServiceTestConfiguration;

import com.helltractor.mall.proto.cart.*;
import com.helltractor.mall.proto.checkout.CheckoutResp;
import com.helltractor.mall.proto.order.OrderResult;
import com.helltractor.mall.proto.order.OrderServiceGrpc;
import com.helltractor.mall.proto.order.PlaceOrderReq;
import com.helltractor.mall.proto.order.PlaceOrderResp;
import com.helltractor.mall.proto.payment.ChargeReq;
import com.helltractor.mall.proto.payment.ChargeResp;
import com.helltractor.mall.proto.payment.PaymentServiceGrpc;
import com.helltractor.mall.proto.product.GetProductReq;
import com.helltractor.mall.proto.product.GetProductResp;
import com.helltractor.mall.proto.product.ProductCatalogServiceGrpc;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static com.helltractor.mall.constant.BaseParamConstant.ORDER_ID;
import static com.helltractor.mall.constant.BaseParamConstant.TRANSACTION_ID;
import static com.helltractor.mall.constant.ModelConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = {
        "grpc.server.in-process-name=test",
        "grpc.server.port=-1",
        "grpc.client.serviceServer.address=in-process:test"
})
@SpringJUnitConfig(ServiceTestConfiguration.class)
@DirtiesContext
class CheckoutServerServiceTest {
    
    @InjectMocks
    private CheckoutServerService checkoutServerService;
    
    @Mock
    private CartServiceGrpc.CartServiceBlockingStub cartClientService;
    
    @Mock
    private OrderServiceGrpc.OrderServiceBlockingStub orderClientService;
    
    @Mock
    private PaymentServiceGrpc.PaymentServiceBlockingStub paymentClientService;
    
    @Mock
    private ProductCatalogServiceGrpc.ProductCatalogServiceBlockingStub productCatalogClientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testCheckoutSuccess() {
        // mock service responses
        GetCartResp mockGetCartResp = GetCartResp.newBuilder().setCart(CART).build();
        when(cartClientService.getCart(any(GetCartReq.class))).thenReturn(mockGetCartResp);
        
        GetProductResp mockGetProductResp = GetProductResp.newBuilder().build();
        when(productCatalogClientService.getProduct(any(GetProductReq.class))).thenReturn(mockGetProductResp);
        
        PlaceOrderResp mockPlaceOrderResp = PlaceOrderResp.newBuilder()
                .setOrder(OrderResult.newBuilder()
                        .setOrderId(ORDER_ID)
                        .build())
                .build();
        when(orderClientService.placeOrder(any(PlaceOrderReq.class))).thenReturn(mockPlaceOrderResp);
        
        ChargeResp mockChargeResp = ChargeResp.newBuilder().setTransactionId(TRANSACTION_ID).build();
        when(paymentClientService.charge(any(ChargeReq.class))).thenReturn(mockChargeResp);
        
        // call service method
        StreamRecorder<CheckoutResp> responseObserver = StreamRecorder.create();
        checkoutServerService.checkout(CHECKOUT_REQ, responseObserver);
        
        // verify response
        assertNull(responseObserver.getError());
        
        CheckoutResp response = responseObserver.getValues().get(0);
        assertEquals(ORDER_ID, response.getOrderId());
        assertEquals(TRANSACTION_ID, response.getTransactionId());
        
        // verify all service interactions
        verify(cartClientService).getCart(any(GetCartReq.class));
        verify(productCatalogClientService, times(mockGetCartResp.getCart().getItemsCount())).getProduct(any(GetProductReq.class));
        verify(orderClientService).placeOrder(any(PlaceOrderReq.class));
        verify(paymentClientService).charge(any(ChargeReq.class));
    }
    
    @Test
    void testCheckoutEmptyCart() {
        // mock service response
        GetCartResp mockGetCartResp = GetCartResp.newBuilder().build();
        when(cartClientService.getCart(any(GetCartReq.class))).thenReturn(mockGetCartResp);
        
        // call service method
        StreamRecorder<CheckoutResp> responseObserver = StreamRecorder.create();
        checkoutServerService.checkout(CHECKOUT_REQ, responseObserver);
        
        // verify response
        assertNotNull(responseObserver.getError());
        assertEquals("Cart is empty", responseObserver.getError().getMessage());
    }
    
}