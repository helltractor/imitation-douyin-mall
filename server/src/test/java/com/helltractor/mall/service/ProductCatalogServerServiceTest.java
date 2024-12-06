package com.helltractor.mall.service;

import com.helltractor.mall.config.ServiceConfiguration;
import com.helltractor.mall.mapper.ProductCatalogServiceMapper;
import com.helltractor.mall.proto.product.*;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static com.helltractor.mall.constant.BaseParamConstant.*;
import static com.helltractor.mall.constant.ModelConstant.PRODUCT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        "grpc.server.in-process-name=test",
        "grpc.server.port=-1",
        "grpc.client.serviceServer.address=in-process:test"
})
@SpringJUnitConfig(ServiceConfiguration.class)
@DirtiesContext
public class ProductCatalogServerServiceTest {
    
    @InjectMocks
    private ProductCatalogServerService productCatalogServerService;
    
    @Mock
    private ProductCatalogServiceMapper productCatalogServiceMapper;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testListProducts() {
        List<Product> products = List.of(PRODUCT);
        when(productCatalogServiceMapper.listProducts(PAGE, PAGE_SIZE, CATEGORY_NAME)).thenReturn(products);
        
        ListProductsReq request = ListProductsReq.newBuilder()
                .setPage(PAGE)
                .setPageSize(PAGE_SIZE)
                .setCategoryName(CATEGORY_NAME)
                .build();
        StreamRecorder<ListProductsResp> responseObserver = StreamRecorder.create();
        productCatalogServerService.listProducts(request, responseObserver);
        
        assertNull(responseObserver.getError());
        ListProductsResp response = responseObserver.getValues().get(0);
        assertEquals(products, response.getProductsList());
        
        verify(productCatalogServiceMapper).listProducts(PAGE, PAGE_SIZE, CATEGORY_NAME);
    }

    @Test
    void testGetProduct() {
        when(productCatalogServiceMapper.searchProductById(PRODUCT_ID_ONE)).thenReturn(PRODUCT);
        
        GetProductReq request = GetProductReq.newBuilder().setId(PRODUCT_ID_ONE).build();
        StreamRecorder<GetProductResp> responseObserver = StreamRecorder.create();
        productCatalogServerService.getProduct(request, responseObserver);
        
        assertNull(responseObserver.getError());
        GetProductResp response = responseObserver.getValues().get(0);
        assertEquals(PRODUCT, response.getProduct());
        
        verify(productCatalogServiceMapper).searchProductById(PRODUCT_ID_ONE);
    }
    
    @Test
    void testGetProductNotId() {
        GetProductReq request = GetProductReq.newBuilder().build();
        StreamRecorder<GetProductResp> responseObserver = StreamRecorder.create();
        productCatalogServerService.getProduct(request, responseObserver);
        
        assertNotNull(responseObserver.getError());
        assertEquals("Product id is required", responseObserver.getError().getMessage());
    }
    
    @Test
    void testSearchProducts() {
        List<Product> products = List.of(PRODUCT);
        when(productCatalogServiceMapper.searchProductByQuery(QUERY)).thenReturn(products);
        
        SearchProductsReq request = SearchProductsReq.newBuilder().setQuery(QUERY).build();
        StreamRecorder<SearchProductsResp> responseObserver = StreamRecorder.create();
        productCatalogServerService.searchProducts(request, responseObserver);
        
        assertNull(responseObserver.getError());
        SearchProductsResp response = responseObserver.getValues().get(0);
        assertEquals(products, response.getResultsList());
        
        verify(productCatalogServiceMapper).searchProductByQuery(QUERY);
    }

}