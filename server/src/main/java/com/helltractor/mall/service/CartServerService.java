package com.helltractor.mall.service;

import com.helltractor.mall.entity.CartEntity;
import com.helltractor.mall.mapper.CartServiceMapper;
import com.helltractor.mall.handler.TransferEntityHandler;
import com.helltractor.mall.proto.cart.*;
import com.helltractor.mall.proto.product.GetProductReq;
import com.helltractor.mall.proto.product.GetProductResp;
import com.helltractor.mall.proto.product.Product;
import com.helltractor.mall.proto.product.ProductCatalogServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@GrpcService
public class CartServerService extends CartServiceGrpc.CartServiceImplBase {
    
    @Autowired
    TransferEntityHandler handler;
    
    @Autowired
    CartServiceMapper cartServiceMapper;
    
    @GrpcClient("service-server")
    ProductCatalogServiceGrpc.ProductCatalogServiceBlockingStub productCatalogClientService;
    
    @Override
    public void addItem(AddItemReq request, StreamObserver<AddItemResp> responseObserver) {
        try {
            GetProductResp getProductResp = productCatalogClientService.getProduct(GetProductReq.newBuilder().setId(request.getItem().getProductId()).build());
            
            if (getProductResp.getProduct() == Product.getDefaultInstance() || getProductResp.getProduct().getId() == 0) {
                log.error("Product not found");
                throw new IllegalArgumentException("Product not found");
            }
            
            CartEntity cartEntity = handler.transCartEntity(request);
            cartServiceMapper.insertCart(cartEntity);
            
            AddItemResp response = AddItemResp.newBuilder().build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            log.error("Add cartItem failed", e);
            responseObserver.onError(e);
        } finally {
            responseObserver.onCompleted();
        }
    }
    
    @Override
    public void getCart(GetCartReq request, StreamObserver<GetCartResp> responseObserver) {
        try {
            List<CartItem> cartItems = cartServiceMapper.searchCartByUserId(request.getUserId());
            
            Cart cart = Cart.newBuilder().setUserId(request.getUserId()).addAllItems(cartItems).build();
            GetCartResp response = GetCartResp.newBuilder().setCart(cart).build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            log.error("Get cart failed", e);
            responseObserver.onError(e);
        } finally {
            responseObserver.onCompleted();
        }
    }
    
    @Override
    public void emptyCart(EmptyCartReq request, StreamObserver<EmptyCartResp> responseObserver) {
        try {
            cartServiceMapper.deleteCart(request.getUserId());
            
            EmptyCartResp response = EmptyCartResp.newBuilder().build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            log.error("Empty cart failed", e);
            responseObserver.onError(e);
        } finally {
            responseObserver.onCompleted();
        }
    }
    
}