package com.helltractor.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private Integer productId;
    
    private Integer quantity;
    
    private Float cost;
    
}