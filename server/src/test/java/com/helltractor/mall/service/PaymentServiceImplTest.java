package com.helltractor.mall.service;


import com.helltractor.mall.config.ServiceConfiguration;
import com.helltractor.mall.entity.PaymentEntity;
import com.helltractor.mall.handler.TransferEntityHandler;
import com.helltractor.mall.mapper.PaymentServiceMapper;
import com.helltractor.mall.proto.payment.ChargeReq;
import com.helltractor.mall.proto.payment.ChargeResp;
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

import static com.helltractor.mall.constant.BaseParamConstant.*;
import static com.helltractor.mall.constant.ModelConstant.CREDIT_CARD_INFO;
import static com.helltractor.mall.constant.ModelConstant.CREDIT_CARD_INFO_INVALID_NUMBER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@SpringBootTest(properties = {
        "grpc.server.in-process-name=test",
        "grpc.server.port=-1",
        "grpc.client.serviceServer.address=in-process:test"
})
@SpringJUnitConfig(ServiceConfiguration.class)
@DirtiesContext
public class PaymentServiceImplTest {
    
    @InjectMocks
    private PaymentServerService paymentService;
    
    @Autowired
    private TransferEntityHandler handler;
    
    @Mock
    private PaymentServiceMapper paymentServiceMapper;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.paymentService.handler = handler;
    }
    
    @Test
    public void testCharge() {
        ChargeReq chargeReq = ChargeReq.newBuilder()
            .setAmount(AMOUNT)
            .setCreditCard(CREDIT_CARD_INFO)
            .setOrderId(ORDER_ID)
            .setUserId(USER_ID)
            .build();
        StreamRecorder<ChargeResp> responseObserver = StreamRecorder.create();
        paymentService.charge(chargeReq, responseObserver);
        
        assertNull(responseObserver.getError());
        
        ChargeResp chargeResp = responseObserver.getValues().get(0);
        assertNotNull(chargeResp.getTransactionId());

        verify(paymentServiceMapper).insert(any(PaymentEntity.class));
    }
    
    @Test
    void testChargeInvalidCreditCard() {
        ChargeReq chargeReq = ChargeReq.newBuilder()
            .setAmount(AMOUNT)
            .setCreditCard(CREDIT_CARD_INFO_INVALID_NUMBER)
            .setOrderId(ORDER_ID)
            .setUserId(USER_ID)
            .build();
        StreamRecorder<ChargeResp> responseObserver = StreamRecorder.create();
        paymentService.charge(chargeReq, responseObserver);
        
        assertNotNull(responseObserver.getError());
        assertEquals("Invalid credit card", responseObserver.getError().getMessage());
        
        verifyNoInteractions(paymentServiceMapper);
    }
    
}