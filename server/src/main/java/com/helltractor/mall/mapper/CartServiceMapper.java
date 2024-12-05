package com.helltractor.mall.mapper;

import com.helltractor.mall.entity.CartEntity;
import com.helltractor.mall.proto.cart.CartItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartServiceMapper {
    
    void insertCart(CartEntity cartEntity);
    
    void deleteCart(int userId);
    
    List<CartItem> searchCartByUserId(int userId);
    
}