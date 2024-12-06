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
public class CheckoutAddressEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    private Integer userId;
    
    private String country;
    
    private String state;
    
    private String city;
    
    private String streetAddress;
    
    private String zipCode;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
}