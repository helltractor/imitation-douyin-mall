package com.helltractor.mall.config;

import com.helltractor.mall.interceptor.CasbinGrpcServerInterceptor;
import com.helltractor.mall.interceptor.JwtGrpcServerInterceptor;
import com.helltractor.mall.interceptor.LogGrpcInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.context.annotation.Configuration;
import com.alibaba.csp.sentinel.adapter.grpc.SentinelGrpcServerInterceptor;

@Configuration
public class GlobalInterceptorConfiguration {
    
    @GrpcGlobalServerInterceptor
    LogGrpcInterceptor logGrpcInterceptor() {
        return new LogGrpcInterceptor();
    }
    
    /**
     * Register the Sentinel gRPC server interceptor.
     */
    @GrpcGlobalServerInterceptor
    SentinelGrpcServerInterceptor sentinelGrpcServerInterceptor() {
        return new SentinelGrpcServerInterceptor();
    }
    
    /**
     * Register the Casbin gRPC server interceptor.
     */
    @GrpcGlobalServerInterceptor
    CasbinGrpcServerInterceptor casbinGrpcServerInterceptor() {
        return new CasbinGrpcServerInterceptor();
    }
    
    
    /**
     * Register the Jwt gRPC server interceptor.
     */
    @GrpcGlobalServerInterceptor
    JwtGrpcServerInterceptor jwtGrpcServerInterceptor() {
        return new JwtGrpcServerInterceptor();
    }
    
}