package com.helltractor.mall.mapper;

import com.helltractor.mall.proto.cart.CartItem;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartServiceMapperTest {
    
    @Autowired
    private CartServiceMapper cartServiceMapper;
    
    private static final int userId = 0;
    
    private static final int productId = 1;
    
    private static final int quantity = 2;
    
    private static final CartItem cartItem = CartItem.newBuilder()
            .setProductId(productId)
            .setQuantity(quantity)
            .build();
    
    @Test
    public void testUnit() {
        List<CartItem> cartItems = cartServiceMapper.search(userId);
        
        assertTrue(cartItems.isEmpty());
        
        cartServiceMapper.insert(userId, cartItem);
        cartItems = cartServiceMapper.search(userId);
        
        assertFalse(cartItems.isEmpty());
        assertEquals(productId, cartItems.get(0).getProductId());
        assertEquals(quantity, cartItems.get(0).getQuantity());
        
        cartServiceMapper.delete(userId);
        cartItems = cartServiceMapper.search(userId);
        
        assertTrue(cartItems.isEmpty());
    }
    
    // TODO: add more test cases
    // public void testInsertSameProductId() {}
    
}