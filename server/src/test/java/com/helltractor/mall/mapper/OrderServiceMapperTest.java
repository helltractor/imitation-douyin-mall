package com.helltractor.mall.mapper;

import com.helltractor.mall.entity.OrderEntity;
import com.helltractor.mall.proto.order.Address;
import com.helltractor.mall.proto.order.Order;
import com.helltractor.mall.proto.order.OrderItem;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static com.helltractor.mall.constant.BaseParamConstant.*;
import static com.helltractor.mall.constant.ModelConstant.*;
import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderServiceMapperTest {
    
    @Autowired
    private OrderServiceMapper orderServiceMapper;
    
    @Test
    public void testPaidStatusUnion() {
        orderServiceMapper.insertAddress(ORDER_ADDRESS_ENTITY);
        ORDER_ENTITY.setAddressBookId(ORDER_ADDRESS_ENTITY.getId());
        orderServiceMapper.insertOrders(ORDER_ENTITY);
        orderServiceMapper.insertOrderItem(ORDER_DETAIL_ENTITY);
        
        int paidStatus = orderServiceMapper.searchPaidStatus(USER_ID_TEST, ORDER_ENTITY.getOrderId());
        assertEquals(0, paidStatus);

        orderServiceMapper.updatePaidStatus(USER_ID_TEST, ORDER_ENTITY.getOrderId());

        paidStatus = orderServiceMapper.searchPaidStatus(USER_ID_TEST, ORDER_ENTITY.getOrderId());
        assertEquals(1, paidStatus);
    }
    
    @Test
    public void testAddressUnion() {
        orderServiceMapper.insertAddress(ORDER_ADDRESS_ENTITY);
        
        assertNotNull(ORDER_ADDRESS_ENTITY.getId());
        
        Address newAddress = orderServiceMapper.searchAddress(ORDER_ADDRESS_ENTITY.getId());
        
        assertEquals(ORDER_ADDRESS_ENTITY.getCountry(), newAddress.getCountry());
        assertEquals(ORDER_ADDRESS_ENTITY.getState(), newAddress.getState());
        assertEquals(ORDER_ADDRESS_ENTITY.getCity(), newAddress.getCity());
        assertEquals(ORDER_ADDRESS_ENTITY.getStreetAddress(), newAddress.getStreetAddress());
        assertEquals(ORDER_ADDRESS_ENTITY.getZipCode(), newAddress.getZipCode());
    }
    
    @Test
    public void testOrderItemsUnion() {
        orderServiceMapper.insertOrderItems(List.of(ORDER_DETAIL_ENTITY));
        List<OrderItem> orderItems = orderServiceMapper.searchOrderItems(ORDER_DETAIL_ENTITY.getOrderId());
        
        assertEquals(ORDER_DETAIL_ENTITY.getCost(), orderItems.get(0).getCost());
        assertEquals(ORDER_DETAIL_ENTITY.getProductId(), orderItems.get(0).getItem().getProductId());
        assertEquals(ORDER_DETAIL_ENTITY.getQuantity(), orderItems.get(0).getItem().getQuantity());
    }
    
    @Test
    public void testSearchOrder() {
        orderServiceMapper.insertAddress(ORDER_ADDRESS_ENTITY);
        ORDER_ENTITY.setAddressBookId(ORDER_ADDRESS_ENTITY.getId());
        orderServiceMapper.insertOrders(ORDER_ENTITY);
        orderServiceMapper.insertOrderItem(ORDER_DETAIL_ENTITY);
        
        List<Order> orders = orderServiceMapper.searchOrder(USER_ID_TEST);
        
        assertEquals(ORDER_ENTITY.getOrderId(), orders.get(0).getOrderId());
        assertEquals(ORDER_DETAIL_ENTITY.getProductId(), orders.get(0).getOrderItems(0).getItem().getProductId());
    }
    
    @Test
    public void testSearchOrders() {
        orderServiceMapper.insertOrders(ORDER_ENTITY);
        List<OrderEntity> orderEntities = orderServiceMapper.searchOrders(USER_ID_TEST);
        
        assertEquals(ORDER_ENTITY.getOrderId(), orderEntities.get(0).getOrderId());
    }
    
    @Test
    public void testSearchOrderItems() {
        orderServiceMapper.insertOrderItem(ORDER_DETAIL_ENTITY);
        List<OrderItem> orderItems = orderServiceMapper.searchOrderItems(ORDER_ENTITY.getOrderId());
        
        assertEquals(ORDER_DETAIL_ENTITY.getProductId(), orderItems.get(0).getItem().getProductId());
    }
    
}