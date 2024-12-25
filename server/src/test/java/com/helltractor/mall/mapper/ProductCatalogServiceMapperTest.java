package com.helltractor.mall.mapper;

import com.helltractor.mall.config.AbstractIntegrationTest;
import com.helltractor.mall.proto.product.Product;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static com.helltractor.mall.constant.BaseParamConstant.*;
import static com.helltractor.mall.constant.ModelConstant.PRODUCT_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductCatalogServiceMapperTest extends AbstractIntegrationTest {
    
    @Autowired
    ProductCatalogServiceMapper productCatalogServiceMapper;
    
    @Test
    void testInsert() {
        productCatalogServiceMapper.insert(PRODUCT_ENTITY);
        assertNotNull(PRODUCT_ENTITY.getId());
    }
    
    @Test
    void testSearchProductById() {
        productCatalogServiceMapper.insert(PRODUCT_ENTITY);
        Product product = productCatalogServiceMapper.searchProductById(PRODUCT_ENTITY.getId());
        assertEquals(PRODUCT_ENTITY.getName(), product.getName());
        assertEquals(PRODUCT_ENTITY.getDescription(), product.getDescription());
        assertEquals(PRODUCT_ENTITY.getPicture(), product.getPicture());
    }
    
    @Test
    void testSearchProductByQuery() {
        productCatalogServiceMapper.insert(PRODUCT_ENTITY);
        List<Product> products = productCatalogServiceMapper.searchProductByQuery(CATEGORY_NAME);
        assertNotNull(products);
        assertEquals(1, products.size());
    }
    
    @Test
    void testListProducts() {
        productCatalogServiceMapper.insert(PRODUCT_ENTITY);
        List<Product> products = productCatalogServiceMapper.listProducts(PAGE, PAGE_SIZE, CATEGORY_NAME);
        assertNotNull(products);
        assertEquals(1, products.size());
    }
    
    // TODO: test update and delete
    
}