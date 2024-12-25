package com.helltractor.mall.mapper;

import com.helltractor.mall.config.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static com.helltractor.mall.constant.ModelConstant.PAYMENT_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PaymentServiceMapperTest extends AbstractIntegrationTest {
    
    @Autowired
    PaymentServiceMapper paymentServiceMapper;
    
    @Test
    void testInsert() {
        paymentServiceMapper.insert(PAYMENT_ENTITY);
        String transactionId = paymentServiceMapper.searchTransactionId(PAYMENT_ENTITY);
        
        assertEquals(transactionId, PAYMENT_ENTITY.getTransactionId());
    }
    
}