package com.helltractor.mall.mapper;

import com.helltractor.mall.entity.ProductEntity;
import com.helltractor.mall.proto.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductCatalogServiceMapper {
    
    void insert(ProductEntity productEntity);
    
    Product searchProductById(int id);
    
    List<Product> searchProductByQuery(String query);
    
    List<Product> listProducts(int page, long pageSize, String categoryName);
    
    void update(ProductEntity productEntity);
    
    void delete(int id);
    
}