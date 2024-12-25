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
public class UserEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    private String password;
    
    private String email;
    
    private String confirmPassword;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
}