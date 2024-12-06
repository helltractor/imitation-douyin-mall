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
public class CheckoutEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    private Integer userId;
    
    private String orderId;
    
    private Integer addressBookId;
    
    private Integer creditCardId;
    
    private String transactionId;
    
    private String email;
    
    private String firstname;
    
    private String lastname;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
}