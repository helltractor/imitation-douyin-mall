package com.helltractor.mall.mapper;

import com.helltractor.mall.proto.cart.CartItem;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static com.helltractor.mall.constant.BaseParamConstant.*;
import static com.helltractor.mall.constant.ModelConstant.CART_ENTITY;
import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartServiceMapperTest {
    
    @Autowired
    CartServiceMapper cartServiceMapper;
    
    @Test
    void testUnion() {
        List<CartItem> cartItems = cartServiceMapper.searchCartByUserId(USER_ID_TEST);
        
        assertTrue(cartItems.isEmpty());
        
        cartServiceMapper.insertCart(CART_ENTITY);
        cartItems = cartServiceMapper.searchCartByUserId(USER_ID_TEST);
        
        assertFalse(cartItems.isEmpty());
        assertEquals(CART_ENTITY.getProductId(), cartItems.get(0).getProductId());
        assertEquals(CART_ENTITY.getQuantity(), cartItems.get(0).getQuantity());
        
        cartServiceMapper.deleteCart(USER_ID_TEST);
        cartItems = cartServiceMapper.searchCartByUserId(USER_ID_TEST);
        
        assertTrue(cartItems.isEmpty());
    }
    
    @Test
    void testInsertCartSameProductId() {
        for (int i = 0; i < CART_ENTITY.getProductId(); i++) {
            cartServiceMapper.insertCart(CART_ENTITY);
        }
        List<CartItem> cartItems = cartServiceMapper.searchCartByUserId(USER_ID_TEST);
        
        assertFalse(cartItems.isEmpty());
        assertEquals(CART_ENTITY.getQuantity() * CART_ENTITY.getProductId(), cartItems.get(0).getQuantity());
    }
    
}