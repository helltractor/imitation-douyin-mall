package com.helltractor.mall.constant;

import com.alibaba.fastjson.JSON;
import com.helltractor.mall.entity.*;
import com.helltractor.mall.proto.cart.Cart;
import com.helltractor.mall.proto.cart.CartItem;
import com.helltractor.mall.proto.checkout.Address;
import com.helltractor.mall.proto.checkout.CheckoutReq;
import com.helltractor.mall.proto.order.Order;
import com.helltractor.mall.proto.order.OrderItem;
import com.helltractor.mall.proto.payment.CreditCardInfo;
import com.helltractor.mall.proto.product.Product;

import static com.helltractor.mall.constant.BaseParamConstant.*;

public class ModelConstant {
    
    public static final Address ADDRESS = Address.newBuilder()
            .setCountry(COUNTRY)
            .setState(STATE)
            .setCity(CITY)
            .setStreetAddress(STREET_ADDRESS)
            .setZipCode(ZIP_CODE)
            .build();
    
    public static final CartItem CART_ITEM_ONE = CartItem.newBuilder()
            .setProductId(PRODUCT_ID_ONE)
            .setQuantity(QUANTITY_ONE)
            .build();
    
    public static final CartItem CART_ITEM_TWO = CartItem.newBuilder()
            .setProductId(PRODUCT_ID_TWO)
            .setQuantity(QUANTITY_TWO)
            .build();
    
    public static final Cart CART = Cart.newBuilder()
            .setUserId(USER_ID_TEST)
            .addItems(CART_ITEM_ONE)
            .addItems(CART_ITEM_TWO)
            .build();
    
    public static final CreditCardInfo CREDIT_CARD_INFO = CreditCardInfo.newBuilder()
            .setCreditCardNumber(CREDIT_CARD_NUMBER)
            .setCreditCardCvv(CREDIT_CARD_CVV)
            .setCreditCardExpirationYear(CREDIT_CARD_EXPIRATION_YEAR)
            .setCreditCardExpirationMonth(CREDIT_CARD_EXPIRATION_MONTH)
            .build();
    
    public static final CreditCardInfo CREDIT_CARD_INFO_INVALID_NUMBER = CreditCardInfo.newBuilder()
            .setCreditCardNumber(CREDIT_CARD_NUMBER_INVALID)
            .setCreditCardCvv(CREDIT_CARD_CVV)
            .setCreditCardExpirationYear(CREDIT_CARD_EXPIRATION_YEAR)
            .setCreditCardExpirationMonth(CREDIT_CARD_EXPIRATION_MONTH)
            .build();
    
    public static final com.helltractor.mall.proto.order.Address ORDER_ADDRESS = com.helltractor.mall.proto.order.Address.newBuilder()
            .setCountry(COUNTRY)
            .setState(STATE)
            .setCity(CITY)
            .setStreetAddress(STREET_ADDRESS)
            .setZipCode(Integer.parseInt(ZIP_CODE))
            .build();
    
    public static final OrderItem ORDER_ITEM_ONE = OrderItem.newBuilder()
            .setItem(CART_ITEM_ONE)
            .setCost(COST)
            .build();
    
    public static final Order ORDER = Order.newBuilder()
            .setOrderId(ORDER_ID)
            .setUserId(USER_ID)
            .setAddress(ORDER_ADDRESS)
            .setEmail(EMAIL)
            .setUserCurrency(USER_CURRENCY_USD)
            .addOrderItems(ORDER_ITEM_ONE)
            .build();
    
    public static final Product PRODUCT = Product.newBuilder()
            .setId(PRODUCT_ID_ONE)
            .setName(PRODUCT_NAME_ONE)
            .setDescription(DESCRIPTION)
            .setPicture(PICTURE)
            .addAllCategories(CATEGORIES)
            .setPrice(PRICE)
            .build();
    
    public static final CartEntity CART_ENTITY = CartEntity.builder()
            .userId(USER_ID_TEST)
            .productId(PRODUCT_ID_ONE)
            .quantity(QUANTITY_RANDOM)
            .build();
    
    public static final CreditCardEntity CREDIT_CARD_ENTITY = CreditCardEntity.builder()
            .userId(USER_ID_TEST)
            .creditCardNumber(CREDIT_CARD_NUMBER)
            .creditCardCvv(CREDIT_CARD_CVV)
            .creditCardExpirationYear(CREDIT_CARD_EXPIRATION_YEAR)
            .creditCardExpirationMonth(CREDIT_CARD_EXPIRATION_MONTH)
            .build();
    
    public static final OrderAddressEntity ORDER_ADDRESS_ENTITY = OrderAddressEntity.builder()
            .userId(USER_ID_TEST)
            .country(COUNTRY)
            .state(STATE)
            .city(CITY)
            .streetAddress(STREET_ADDRESS)
            .zipCode(Integer.parseInt(ZIP_CODE))
            .build();
    
    public static final OrderDetailEntity ORDER_DETAIL_ENTITY = OrderDetailEntity.builder()
            .orderId(ORDER_ID)
            .productId(PRODUCT_ID_ONE)
            .quantity(QUANTITY_RANDOM)
            .cost(COST)
            .build();
    
    public static final OrderEntity ORDER_ENTITY = OrderEntity.builder()
            .orderId(ORDER_ID)
            .userId(USER_ID_TEST)
            .addressBookId(ADDRESS_BOOK_ID)
            .email(EMAIL)
            .userCurrency(USER_CURRENCY_USD)
            .build();
    
    public static final PaymentEntity PAYMENT_ENTITY = PaymentEntity.builder()
            .transactionId(TRANSACTION_ID)
            .userId(USER_ID_TEST)
            .orderId(ORDER_ID)
            .amount(AMOUNT)
            .build();
    
    public static final ProductEntity PRODUCT_ENTITY = ProductEntity.builder()
            .name(PRODUCT_NAME_ONE)
            .description(DESCRIPTION)
            .picture(PICTURE)
            .categories(JSON.toJSONString(CATEGORIES))
            .price(PRICE)
            .build();
    
    public static final UserEntity USER_ENTITY = UserEntity.builder()
            .id(USER_ID)
            .email(EMAIL)
            .password(ENCRYPT_PASSWORD)
            .confirmPassword(CONFIRM_PASSWORD)
            .build();
    
    public static final UserEntity USER_ENTITY_TEST = UserEntity.builder()
            .id(USER_ID_TEST)
            .email(EMAIL_RANDOM)
            .password(ENCRYPT_PASSWORD)
            .confirmPassword(CONFIRM_PASSWORD)
            .build();
    
    public static final UserEntity USER_ENTITY_RANDOM = UserEntity.builder()
            .id(USER_ID_RANDOM)
            .email(EMAIL_RANDOM)
            .password(ENCRYPT_PASSWORD)
            .confirmPassword(CONFIRM_PASSWORD)
            .build();
    
    public static final CheckoutReq CHECKOUT_REQ = CheckoutReq.newBuilder()
            .setUserId(USER_ID_TEST)
            .setEmail(EMAIL)
            .setFirstname(FIRSTNAME)
            .setLastname(LASTNAME)
            .setAddress(ADDRESS)
            .setCreditCard(CREDIT_CARD_INFO)
            .build();
    
}