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
public class CartEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer userId;
    
    private Integer productId;
    
    private Integer quantity;
    
    private LocalDateTime createTime;
    
}