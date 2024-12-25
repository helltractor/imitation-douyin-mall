package com.helltractor.mall.service;

import com.helltractor.mall.entity.PaymentEntity;
import com.helltractor.mall.handler.TransferEntityHandler;
import com.helltractor.mall.mapper.PaymentServiceMapper;
import com.helltractor.mall.proto.payment.ChargeReq;
import com.helltractor.mall.proto.payment.ChargeResp;
import com.helltractor.mall.proto.payment.CreditCardInfo;
import com.helltractor.mall.proto.payment.PaymentServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Slf4j
@GrpcService
public class PaymentServerService extends PaymentServiceGrpc.PaymentServiceImplBase {
    
    @Autowired
    TransferEntityHandler handler;
    
    @Autowired
    PaymentServiceMapper paymentServiceMapper;
    
    @Override
    public void charge(ChargeReq request, StreamObserver<ChargeResp> responseObserver) {
        try {
            boolean isValid = validateCreditCard(request.getCreditCard());
            if (!isValid) {
                throw new IllegalArgumentException("Invalid credit card");
            }
            
            PaymentEntity paymentEntity = handler.transTransactionEntity(request);
            paymentServiceMapper.insert(paymentEntity);
            
            ChargeResp response = ChargeResp.newBuilder()
                    .setTransactionId(paymentEntity.getTransactionId())
                    .build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            log.error("Charge failed");
            responseObserver.onError(e);
        } finally {
            responseObserver.onCompleted();
        }
    }
    
    private boolean validateCreditCard(CreditCardInfo creditCardInfo) {
        if (creditCardInfo.getCreditCardNumber() == null || creditCardInfo.getCreditCardNumber().length() < 13 || creditCardInfo.getCreditCardNumber().length() > 19) {
            return false;
        }
        
        if (creditCardInfo.getCreditCardExpirationYear() < LocalDateTime.now().getYear()) {
            return false;
        }
        
        if (creditCardInfo.getCreditCardExpirationMonth() < 1 || creditCardInfo.getCreditCardExpirationMonth() > 12 || creditCardInfo.getCreditCardExpirationYear() == LocalDateTime.now().getYear() && creditCardInfo.getCreditCardExpirationMonth() < LocalDateTime.now().getMonthValue()) {
            return false;
        }
        
        return true;
    }
    
}