package com.helltractor.mall.service;

import com.helltractor.mall.proto.product.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class ProductCatalogClientService {
    
    @GrpcClient("imitation-douyin-mall")
    private ProductCatalogServiceGrpc.ProductCatalogServiceBlockingStub productCatalogServiceBlockingStub;
    
    public ListProductsResp listProducts(ListProductsReq request) {
        return productCatalogServiceBlockingStub.listProducts(request);
    }
    
    public GetProductResp getProduct(GetProductReq request) {
        return productCatalogServiceBlockingStub.getProduct(request);
    }
    
    public SearchProductsResp searchProducts(SearchProductsReq request) {
        return productCatalogServiceBlockingStub.searchProducts(request);
    }
    
}