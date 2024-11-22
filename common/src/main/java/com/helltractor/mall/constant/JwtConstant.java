package com.helltractor.mall.constant;

import io.grpc.Context;
import io.grpc.Metadata;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public class JwtConstant {
    
    public static final SecretKey STRING_KEY = Jwts.SIG.HS256.key().build();
    
    public static final String BEARER_TYPE = "Bearer";
    
    public static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY = Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER);
    
    public static final Context.Key<String> CLIENT_ID_CONTEXT_KEY = Context.key("clientId");
    
    public static final long TTL = 3600000;
    
}