package com.helltractor.mall.mapper;

import com.helltractor.mall.proto.cart.CartItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartServiceMapper {
    
    void insert(int userId, CartItem cart);
    
    void delete(int userId);
    
    List<CartItem> search(int userId);
    
    // TODO: add more methods
    // void searchByProductId(int userId, int productId);
    // void updateByProductId(int userId, int productId, CartItem cart);
    
}