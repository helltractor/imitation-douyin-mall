package com.helltractor.mall.config;

import com.helltractor.mall.interceptor.JwtGrpcClientInterceptor;
import com.helltractor.mall.interceptor.LogGrpcInterceptor;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class GlobalInterceptorConfiguration {
    
    @GrpcGlobalClientInterceptor
    LogGrpcInterceptor logClientInterceptor() {
        return new LogGrpcInterceptor();
    }
    
    @GrpcGlobalClientInterceptor
    JwtGrpcClientInterceptor jwtClientInterceptor() {
        return new JwtGrpcClientInterceptor();
    }
    
}