package com.helltractor.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String transactionId;
    
    private Integer userId;
    
    private String orderId;
    
    private Float amount;
    
    private LocalDateTime createTime;
    
}