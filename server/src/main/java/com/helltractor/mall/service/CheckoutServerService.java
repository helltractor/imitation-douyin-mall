package com.helltractor.mall.service;

import com.helltractor.mall.proto.cart.*;
import com.helltractor.mall.proto.checkout.Address;
import com.helltractor.mall.proto.checkout.CheckoutReq;
import com.helltractor.mall.proto.checkout.CheckoutResp;
import com.helltractor.mall.proto.checkout.CheckoutServiceGrpc;
import com.helltractor.mall.proto.order.*;
import com.helltractor.mall.proto.payment.ChargeReq;
import com.helltractor.mall.proto.payment.ChargeResp;
import com.helltractor.mall.proto.payment.PaymentServiceGrpc;
import com.helltractor.mall.proto.product.GetProductReq;
import com.helltractor.mall.proto.product.GetProductResp;
import com.helltractor.mall.proto.product.Product;
import com.helltractor.mall.proto.product.ProductCatalogServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

/**
 * Run
 * <p>
 * 1. get cart
 * 2. calculate cart
 * 3. create order
 * 4. empty cart
 * 5. pay
 * 6. change order result
 * 7. finish
 **/

@Slf4j
@GrpcService
public class CheckoutServerService extends CheckoutServiceGrpc.CheckoutServiceImplBase {
    
    @GrpcClient("service-server")
    CartServiceGrpc.CartServiceBlockingStub cartClientService;
    
    @GrpcClient("service-server")
    OrderServiceGrpc.OrderServiceBlockingStub orderClientService;
    
    @GrpcClient("service-server")
    PaymentServiceGrpc.PaymentServiceBlockingStub paymentClientService;
    
    @GrpcClient("service-server")
    ProductCatalogServiceGrpc.ProductCatalogServiceBlockingStub productCatalogClientService;
    
    @Override
    public void checkout(CheckoutReq request, StreamObserver<CheckoutResp> responseObserver) {
        try {
            // get cart
            GetCartReq getCartReq = GetCartReq.newBuilder()
                    .setUserId(request.getUserId())
                    .build();
            GetCartResp getCartResp = cartClientService.getCart(getCartReq);
            
            if (getCartResp == null || getCartResp.getCart().getItemsList().isEmpty()) {
                throw new IllegalArgumentException("Cart is empty");
            }
            
            // calculate cart
            List<OrderItem> orderItems = new ArrayList<>();
            float total = 0;
            
            for (CartItem cartItem : getCartResp.getCart().getItemsList()) {
                GetProductReq getProductReq = GetProductReq.newBuilder()
                        .setId(cartItem.getProductId())
                        .build();
                GetProductResp getProductResp = productCatalogClientService.getProduct(getProductReq);
                
                if (getProductResp.getProduct() == Product.getDefaultInstance() || getProductResp.getProduct().getId() == 0) {
                    continue;
                }
                
                float cost = getProductResp.getProduct().getPrice() * cartItem.getQuantity();
                total += cost;
                
                OrderItem orderItem = OrderItem.newBuilder()
                        .setItem(cartItem)
                        .setCost(cost)
                        .build();
                orderItems.add(orderItem);
            }
            
            // create order
            PlaceOrderReq.Builder placeOrderBuilder = PlaceOrderReq.newBuilder()
                    .setUserId(request.getUserId())
                    .setUserCurrency("USD")
                    .addAllOrderItems(orderItems)
                    .setEmail(request.getEmail());
            
            if (request.hasAddress()) {
                Address address = request.getAddress();
                com.helltractor.mall.proto.order.Address orderAddress = com.helltractor.mall.proto.order.Address.newBuilder()
                        .setCountry(address.getCountry())
                        .setCity(address.getCity())
                        .setState(address.getState())
                        .setStreetAddress(address.getStreetAddress())
                        .setZipCode(Integer.parseInt(address.getZipCode()))
                        .build();
                placeOrderBuilder.setAddress(orderAddress);
            }
            
            PlaceOrderResp placeOrderResp = orderClientService.placeOrder(placeOrderBuilder.build());
            
            // empty cart
            EmptyCartReq emptyCartReq = EmptyCartReq.newBuilder()
                    .setUserId(request.getUserId())
                    .build();
            
            cartClientService.emptyCart(emptyCartReq);
            
            // pay
            ChargeReq chargeReq = ChargeReq.newBuilder()
                    .setUserId(request.getUserId())
                    .setOrderId(placeOrderResp.getOrder().getOrderId())
                    .setCreditCard(request.getCreditCard())
                    .setAmount(total)
                    .build();
            
            ChargeResp chargeResp = paymentClientService.charge(chargeReq);
            
            // change order result
            MarkOrderPaidReq markOrderPaidReq = MarkOrderPaidReq.newBuilder()
                    .setUserId(request.getUserId())
                    .setOrderId(placeOrderResp.getOrder().getOrderId())
                    .build();
            
            orderClientService.markOrderPaid(markOrderPaidReq);
            
            // finish
            CheckoutResp checkoutResp = CheckoutResp.newBuilder()
                    .setOrderId(placeOrderResp.getOrder().getOrderId())
                    .setTransactionId(chargeResp.getTransactionId())
                    .build();
            
            responseObserver.onNext(checkoutResp);
        } catch (Exception e) {
            log.error("Checkout failed", e);
            responseObserver.onError(e);
        } finally {
            responseObserver.onCompleted();
        }
    }
    
}