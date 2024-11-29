package com.helltractor.mall.config;

import com.helltractor.mall.entity.ProductEntity;
import com.helltractor.mall.handler.TransferEntityHandler;
import com.helltractor.mall.mapper.*;
import com.helltractor.mall.service.*;

import com.zaxxer.hikari.HikariDataSource;
import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.checkerframework.checker.units.qual.C;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.helltractor.mall.mapper")
@ImportAutoConfiguration({
        GrpcServerAutoConfiguration.class,  // Create required server beans
        GrpcServerFactoryAutoConfiguration.class,   // Select server implementation
        GrpcClientAutoConfiguration.class   // Support @GrpcClient annotation
})
public class ServiceConfiguration {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;
    
    @Bean
    TransferEntityHandler TransferEntityHandler() {
        return new TransferEntityHandler();
    }
    
    @Bean
    AuthClientService authClientService() {
        return new AuthClientService();
    }
    
    @Bean
    CartClientService cartClientService() {
        return new CartClientService();
    }
    
    @Bean
    OrderClientService orderClientService() {
        return new OrderClientService();
    }
    
    @Bean
    PaymentClientService paymentClientService() {
        return new PaymentClientService();
    }
    
    @Bean
    ProductCatalogClientService productCatalogClientService() {
        return new ProductCatalogClientService();
    }
    
    @Bean
    UserClientService userClientService() {
        return new UserClientService();
    }
    
    @Bean
    AuthServerService authService() {
        return new AuthServerService();
    }
    
    @Bean
    CartServerService cartService() {
        return new CartServerService();
    }
    
    @Bean
    CheckoutServerService checkoutService() {
        return new CheckoutServerService();
    }
    
    @Bean
    OrderServerService orderServerService() {
        return new OrderServerService();
    }
    
    @Bean
    PaymentServerService paymentService() {
        return new PaymentServerService();
    }
    
    @Bean
    ProductCatalogServerService productCatalogService() {
        return new ProductCatalogServerService();
    }
    
    @Bean
    UserServerService userService() {
        return new UserServerService();
    }
    
    @Bean
    DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        // set the location of the MyBatis mapping file
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(mapperLocations)
        );
        return sessionFactory.getObject();
    }

}