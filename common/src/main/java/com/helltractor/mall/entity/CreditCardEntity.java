package com.helltractor.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer userId;
    
    private String creditCardNumber;
    
    private Integer creditCardCvv;
    
    private Integer creditCardExpirationYear;
    
    private Integer creditCardExpirationMonth;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
}