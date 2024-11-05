package com.helltractor.mall.service;

import com.helltractor.mall.proto.product.ProductCatalogServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ProductCatalogService extends ProductCatalogServiceGrpc.ProductCatalogServiceImplBase {
}