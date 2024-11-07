package com.helltractor.mall.config;

import com.helltractor.mall.interceptor.LogGrpcInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class GlobalInterceptorConfiguration {
    
    @GrpcGlobalServerInterceptor
    LogGrpcInterceptor logGrpcInterceptor() {
        return new LogGrpcInterceptor();
    }
}
