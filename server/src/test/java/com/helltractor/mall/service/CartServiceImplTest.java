package com.helltractor.mall.service;

import com.helltractor.mall.config.ServicesTestConfiguration;
import com.helltractor.mall.proto.cart.*;

import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig(classes = ServicesTestConfiguration.class) // manual import configuration
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // autoconfigure
class CartServiceImplTest {
    
    @Autowired
    private CartServiceImpl cartService;
    
    private static final int userId = 0;
    
    private static final int productId = 1;
    
    private static final int quantity = 2;
    
    private static final CartItem cartItem = CartItem.newBuilder()
            .setProductId(productId)
            .setQuantity(quantity)
            .build();
    
    @Test
    @Order(1)
    void testAddItem() throws Exception {
        AddItemReq request = AddItemReq.newBuilder()
                .setUserId(userId)
                .setItem(cartItem)
                .build();
        AddItemResp response = AddItemResp.newBuilder().build();
        StreamRecorder<AddItemResp> responseObserver = StreamRecorder.create();
        
        cartService.addItem(request, responseObserver);
        
        assertNull(responseObserver.getError());
        assertEquals(response, responseObserver.getValues().get(0));
    }
    
    @Test
    @Order(2)
    void testGetCart() {
        List<CartItem> cartItems = new ArrayList<>();
        GetCartReq request = GetCartReq.newBuilder()
                .setUserId(userId)
                .build();
        cartItems.add(cartItem);
        Cart cart = Cart.newBuilder().addAllItems(cartItems).build();
        GetCartResp response = GetCartResp.newBuilder().setCart(cart).build();
        StreamRecorder<GetCartResp> responseObserver = StreamRecorder.create();
        
        cartService.getCart(request, responseObserver);
        
        assertNull(responseObserver.getError());
        assertEquals(response, responseObserver.getValues().get(0));
    }
    
    @Test
    @Order(3)
    void testEmptyCart() {
        EmptyCartReq request = EmptyCartReq.newBuilder()
                .setUserId(userId)
                .build();
        EmptyCartResp response = EmptyCartResp.newBuilder().build();
        StreamRecorder<EmptyCartResp> responseObserver = StreamRecorder.create();
        
        cartService.emptyCart(request, responseObserver);
        
        assertNull(responseObserver.getError());
        assertEquals(response, responseObserver.getValues().get(0));
    }
    
}