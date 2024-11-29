package com.helltractor.mall.service;

import com.helltractor.mall.config.ServiceConfiguration;
import com.helltractor.mall.entity.CartEntity;
import com.helltractor.mall.handler.TransferEntityHandler;
import com.helltractor.mall.mapper.CartServiceMapper;
import com.helltractor.mall.proto.cart.*;

import com.helltractor.mall.proto.product.GetProductReq;
import com.helltractor.mall.proto.product.GetProductResp;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

import static com.helltractor.mall.constant.BaseParamConstant.*;
import static com.helltractor.mall.constant.ModelConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = {
        "grpc.server.in-process-name=test",
        "grpc.server.port=-1",
        "grpc.client.imitationDouyinMall.address=in-process:test"
})
@SpringJUnitConfig(classes = ServiceConfiguration.class)
@DirtiesContext
class CartServerServiceTest {
    
    @InjectMocks
    private CartServerService cartServerService;
    
    @Autowired
    private TransferEntityHandler handler;
    
    @Mock
    private CartServiceMapper cartServiceMapper;
    
    @Mock
    private ProductCatalogClientService productCatalogClientService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.cartServerService.handler = handler;
    }
    
    @Test
    void testAddItem() {
        GetProductResp mockGetProductResp = GetProductResp.newBuilder()
                .setProduct(PRODUCT)
                .build();
        when(productCatalogClientService.getProduct(any(GetProductReq.class))).thenReturn(mockGetProductResp);
        
        AddItemReq request = AddItemReq.newBuilder().setUserId(USER_ID).setItem(CART_ITEM_ONE).build();
        StreamRecorder<AddItemResp> responseObverse = StreamRecorder.create();
        cartServerService.addItem(request, responseObverse);
        AddItemResp response = responseObverse.getValues().get(0);
        
        assertNull(responseObverse.getError());
        assertEquals(AddItemResp.newBuilder().build(), response);
        
        verify(cartServiceMapper).insertCart(any(CartEntity.class));
        verify(productCatalogClientService).getProduct(any(GetProductReq.class));
    }
    
    @Test
    void testAddItemProductNotFound() {
        GetProductResp mockGetProductResp = GetProductResp.newBuilder().build();
        when(productCatalogClientService.getProduct(any(GetProductReq.class))).thenReturn(mockGetProductResp);
        
        AddItemReq request = AddItemReq.newBuilder()
                .setUserId(USER_ID)
                .setItem(CART_ITEM_ONE)
                .build();
        StreamRecorder<AddItemResp> responseObverse = StreamRecorder.create();
        cartServerService.addItem(request, responseObverse);
        
        assertNotNull(responseObverse.getError());
        assertEquals("Product not found", responseObverse.getError().getMessage());
        
        verifyNoInteractions(cartServiceMapper);
        verify(productCatalogClientService).getProduct(any(GetProductReq.class));
    }
    
    @Test
    void testGetCart() {
        List<CartItem> cartItems = List.of(CART_ITEM_ONE, CART_ITEM_TWO);
        when(cartServiceMapper.searchCartByUserId(USER_ID)).thenReturn(cartItems);
        
        GetCartReq request = GetCartReq.newBuilder()
                .setUserId(USER_ID)
                .build();
        StreamRecorder<GetCartResp> responseObverse = StreamRecorder.create();
        cartServerService.getCart(request, responseObverse);
        
        assertNull(responseObverse.getError());
        GetCartResp response = responseObverse.getValues().get(0);
        assertEquals(CART, response.getCart());
        
        verify(cartServiceMapper).searchCartByUserId(USER_ID);
    }
    
    @Test
    void testEmptyCart() {
        EmptyCartReq request = EmptyCartReq.newBuilder()
                .setUserId(USER_ID)
                .build();
        StreamRecorder<EmptyCartResp> responseObverse = StreamRecorder.create();
        cartServerService.emptyCart(request, responseObverse);
        
        assertNull(responseObverse.getError());
        EmptyCartResp response = responseObverse.getValues().get(0);
        assertEquals(EmptyCartResp.newBuilder().build(), response);
        
        verify(cartServiceMapper).deleteCart(USER_ID);
    }
    
}