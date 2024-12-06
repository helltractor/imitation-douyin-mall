package com.helltractor.mall.entity;

import com.alibaba.fastjson.JSON;
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
public class ProductEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    private String name;
    
    private String description;
    
    private String picture;
    
    private Float price;
    
    private String categories;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
}