package com.helltractor.mall.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CasbinGrpcServerInterceptor implements ServerInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(CasbinGrpcServerInterceptor.class);
    
    @Autowired
    private Enforcer enforcer;
    
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        // TODO: Add Casbin rules
        String userId = metadata.get(Metadata.Key.of("user-id", Metadata.ASCII_STRING_MARSHALLER));
        
        boolean hasPermission = enforcer.enforce(userId, "data", "read");
        
        if (!hasPermission) {
            serverCall.close(Status.PERMISSION_DENIED.withDescription("Access Denied"), metadata);
            return new ServerCall.Listener<ReqT>() {};
        }
        
        return serverCallHandler.startCall(serverCall, metadata);
    }
}
