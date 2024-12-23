package com.helltractor.mall.interceptor;

import com.helltractor.mall.constant.JwtConstant;
import com.helltractor.mall.util.JwtUtil;
import io.grpc.*;

import static java.lang.String.format;

public class JwtGrpcClientInterceptor implements ClientInterceptor {
    
    private static final String SUBJECT = "service-client";
    
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> methodDescriptor,
            CallOptions callOptions,
            Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(methodDescriptor, callOptions.withCompression("gzip"))) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                if (!headers.containsKey(JwtConstant.AUTHORIZATION_METADATA_KEY)) {
                    String token = JwtUtil.createJWT(SUBJECT);
                    headers.put(JwtConstant.AUTHORIZATION_METADATA_KEY, format("%s %s", JwtConstant.BEARER_TYPE, token));
                }
                super.start(responseListener, headers);
            }
        };
    }
    
}