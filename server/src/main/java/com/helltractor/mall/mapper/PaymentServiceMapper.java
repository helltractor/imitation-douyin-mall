package com.helltractor.mall.mapper;

import com.helltractor.mall.entity.PaymentEntity;
import com.helltractor.mall.proto.checkout.CheckoutResp;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentServiceMapper {
    
    void insert(PaymentEntity paymentEntity);
    
    CheckoutResp searchIdAndOrderId(int userId, int creditCardId);
    
    String searchTransactionId(PaymentEntity paymentEntity);
    
}