package com.helltractor.mall.constant;

import com.google.protobuf.LazyStringArrayList;
import com.helltractor.mall.utils.JwtUtil;
import com.helltractor.mall.utils.PasswordUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class BaseParamConstant {
    
    public static final Integer USER_ID = 1;
    public static final Integer USER_ID_TEST = 0;
    public static final Integer USER_ID_RANDOM = new Random().nextInt(1000);
    
    public static final String USER_CURRENCY_USD = "USD";
    public static final String USER_CURRENCY_CNY = "CNY";
    
    public static final Integer PRODUCT_ID_ONE = 1;
    public static final Integer PRODUCT_ID_TWO = 2;
    public static final Integer PRODUCT_ID_RANDOM = new Random().nextInt(1000);
    
    public static final String PRODUCT_NAME_PREFIX = "product";
    public static final String PRODUCT_NAME_ONE = PRODUCT_NAME_PREFIX + PRODUCT_ID_ONE;
    public static final String PRODUCT_NAME_TWO = PRODUCT_NAME_PREFIX + PRODUCT_ID_TWO;
    public static final String PRODUCT_NAME_RANDOM = PRODUCT_NAME_PREFIX + PRODUCT_ID_RANDOM;
    
    public static final Integer QUANTITY_ONE = 1;
    public static final Integer QUANTITY_TWO = 2;
    public static final Integer QUANTITY_RANDOM = new Random().nextInt(1000);
    
    public static final String DESCRIPTION = "description";
    public static final String PICTURE = "picture";
    public static final Float PRICE = BigDecimal.valueOf(new Random().nextFloat() * 10000)
            .setScale(2, RoundingMode.HALF_UP)
            .floatValue();
    
    public static final Integer PAGE = 0;
    public static final Integer PAGE_SIZE = 10;
    public static final String CATEGORY_NAME = "category";
    public static final LazyStringArrayList CATEGORIES = new LazyStringArrayList(){{
        add(CATEGORY_NAME);
    }};
    public static final String QUERY = "query";
    
    public static final String ORDER_ID = "orderId";
    public static final String ORDER_ID_UUID = UUID.randomUUID().toString();
    public static final String TRANSACTION_ID = "transactionId";
    public static final String TRANSACTION_ID_UUID = UUID.randomUUID().toString();
    
    public static final Integer ADDRESS_BOOK_ID = 1;
    public static final String COUNTRY = "China";
    public static final String STATE = "Beijing";
    public static final String CITY = "Beijing";
    public static final String STREET_ADDRESS = "Tsinghua University";
    public static final String ZIP_CODE = "100084";
    
    public static final String CREDIT_CARD_NUMBER = "1234567890123458";
    public static final String CREDIT_CARD_NUMBER_INVALID = "1";
    public static final Integer CREDIT_CARD_CVV = 123456;
    public static final Integer CREDIT_CARD_EXPIRATION_YEAR = LocalDateTime.now().getYear();
    public static final Integer CREDIT_CARD_EXPIRATION_YEAR_INVALID = LocalDateTime.now().getYear() - 1;
    public static final Integer CREDIT_CARD_EXPIRATION_MONTH = LocalDateTime.now().getMonthValue();
    public static final Integer CREDIT_CARD_EXPIRATION_MONTH_INVALID = LocalDateTime.now().getMonthValue() - 1;
    
    public static final String EMAIL_PREFIX = "mall";
    public static final String EMAIL_SUFFIX = "@helltractor.top";
    public static final String EMAIL = EMAIL_PREFIX + USER_ID + EMAIL_SUFFIX;
    public static final String EMAIL_RANDOM = EMAIL_PREFIX + USER_ID_RANDOM + EMAIL_SUFFIX;
    
    public static final String PASSWORD = "password";
    public static final String ENCRYPT_PASSWORD = PasswordUtil.encryptPassword(PASSWORD);
    public static final String CONFIRM_PASSWORD = "confirmPassword";
    
    public static final String FIRSTNAME = "Mall";
    public static final String LASTNAME = "Helltractor";
    
    public static final String TOKEN = JwtUtil.createJWT(Map.of(JwtClaimConstant.USER_ID, USER_ID_TEST));
    public static final String TOKEN_EXPIRED = JwtUtil.createJWT(Map.of(JwtClaimConstant.USER_ID, USER_ID_TEST), System.currentTimeMillis() - JwtConstant.TTL);
    
    public static final Float AMOUNT = BigDecimal.valueOf(new Random().nextFloat() * 10000)
            .setScale(2, RoundingMode.HALF_UP)
            .floatValue();
    public static final Float COST = BigDecimal.valueOf(new Random().nextFloat() * 10000)
            .setScale(2, RoundingMode.HALF_UP)
            .floatValue();
    
}