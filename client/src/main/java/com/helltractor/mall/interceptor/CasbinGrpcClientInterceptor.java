package com.helltractor.mall.interceptor;

import io.grpc.*;

public class CasbinGrpcClientInterceptor implements ClientInterceptor {
    
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> methodDescriptor,
            CallOptions callOptions,
            Channel channel) {
        return null;
    }
    
}