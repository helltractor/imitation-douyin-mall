package com.helltractor.mall.interceptor;

import com.helltractor.mall.constant.Constant;
import com.helltractor.mall.util.JwtUtil;
import io.grpc.*;
import org.springframework.beans.factory.annotation.Value;

import static java.lang.String.format;

public class JwtGrpcClientInterceptor implements ClientInterceptor {
    
    @Value("spring.application.name")
    private static String SUBJECT;
    
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> methodDescriptor,
            CallOptions callOptions,
            Channel channel) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(channel.newCall(methodDescriptor, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                if (!headers.containsKey(Constant.AUTHORIZATION_METADATA_KEY)) {
                    String token = JwtUtil.createJWT(SUBJECT);
                    headers.put(Constant.AUTHORIZATION_METADATA_KEY, format("%s %s", Constant.BEARER_TYPE, token));
                }
                super.start(responseListener, headers);
            }
        };
    }
    
}