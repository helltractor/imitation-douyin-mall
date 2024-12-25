package com.helltractor.mall.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseEntity<T> implements Serializable {
    
    private Integer code;
    
    private String msg;
    
    private T data;
    
    public static <T> ResponseEntity<T> success() {
        ResponseEntity<T> responseEntity = new ResponseEntity<T>();
        responseEntity.code = 1;
        return responseEntity;
    }
    
    public static <T> ResponseEntity<T> success(T object) {
        ResponseEntity<T> responseEntity = new ResponseEntity<T>();
        responseEntity.data = object;
        responseEntity.code = 1;
        return responseEntity;
    }
    
    public static <T> ResponseEntity<T> error(String msg) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.msg = msg;
        responseEntity.code = 0;
        return responseEntity;
    }
    
}