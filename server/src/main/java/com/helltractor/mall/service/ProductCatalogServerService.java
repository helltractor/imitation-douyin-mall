package com.helltractor.mall.service;

import com.helltractor.mall.mapper.ProductCatalogServiceMapper;
import com.helltractor.mall.proto.product.*;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@GrpcService
public class ProductCatalogServerService extends ProductCatalogServiceGrpc.ProductCatalogServiceImplBase {
    
    @Autowired
    ProductCatalogServiceMapper productCatalogServiceMapper;
    
    @Override
    public void listProducts(ListProductsReq request, StreamObserver<ListProductsResp> responseObserver) {
        try {
            List<Product> products = productCatalogServiceMapper.listProducts(request.getPage(), request.getPageSize(), request.getCategoryName());
            ListProductsResp response = ListProductsResp.newBuilder()
                    .addAllProducts(products)
                    .build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            log.error("List products failed");
            responseObserver.onError(e);
        } finally {
            responseObserver.onCompleted();
        }
    }
    
    @Override
    public void getProduct(GetProductReq request, StreamObserver<GetProductResp> responseObserver) {
        try {
            if (request == null || request.getId() == 0) {
                throw new IllegalArgumentException("Product id is required");
            }
            Product product = productCatalogServiceMapper.searchProductById(request.getId());
            GetProductResp response = GetProductResp.newBuilder()
                    .setProduct(product)
                    .build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            log.error("Get product failed");
            responseObserver.onError(e);
        } finally {
            responseObserver.onCompleted();
        }
    }
    
    @Override
    public void searchProducts(SearchProductsReq request, StreamObserver<SearchProductsResp> responseObserver) {
        try {
            List<Product> products = productCatalogServiceMapper.searchProductByQuery(request.getQuery());
            SearchProductsResp response = SearchProductsResp.newBuilder()
                    .addAllResults(products)
                    .build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            log.error("Search products failed");
            responseObserver.onError(e);
        } finally {
            responseObserver.onCompleted();
        }
    }
    
}