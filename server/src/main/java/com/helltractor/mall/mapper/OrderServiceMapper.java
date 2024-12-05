package com.helltractor.mall.mapper;

import com.helltractor.mall.entity.OrderAddressEntity;
import com.helltractor.mall.entity.OrderDetailEntity;
import com.helltractor.mall.entity.OrderEntity;
import com.helltractor.mall.proto.order.Address;
import com.helltractor.mall.proto.order.Order;
import com.helltractor.mall.proto.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderServiceMapper {
    
    void insertOrders(OrderEntity order);
    
    void insertAddress(OrderAddressEntity orderAddressEntity);
    
    void insertOrderItem(OrderDetailEntity orderDetailEntity);
    
    void insertOrderItems(List<OrderDetailEntity> orderDetailEntities);
    
    Address searchAddress(int addressBookId);
    
    List<Order> searchOrder(int userId);
    
    List<OrderEntity> searchOrders(int userId);
    
    List<OrderItem> searchOrderItems(String orderId);
    
    int searchPaidStatus(int userId, String orderId);
    
    void updateAddress(OrderAddressEntity orderAddressEntity);
    
    void updatePaidStatus(int userId, String orderId);
    
}