package com.helltractor.mall.service;

import com.helltractor.mall.proto.product.GetProductReq;
import com.helltractor.mall.proto.product.ListProductsReq;
import com.helltractor.mall.proto.product.ProductCatalogServiceGrpc;
import com.helltractor.mall.proto.product.SearchProductsReq;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ProductCatalogService extends ProductCatalogServiceGrpc.ProductCatalogServiceImplBase {
    
    public ListProductsReq listProducts(ListProductsReq request) {
        return ListProductsReq.newBuilder().build();
    }
    
    public GetProductReq getProduct(GetProductReq request) {
        return GetProductReq.newBuilder().build();
    }
    
    public SearchProductsReq searchProducts(SearchProductsReq request) {
        return SearchProductsReq.newBuilder().build();
    }
}