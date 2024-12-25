package com.helltractor.mall.constant;

import io.grpc.Context;
import io.grpc.Metadata;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public final class Constant {
    
    public static final String JWT_SIGNING_KEY = "L8hHXsaQOUjk5rg7XPGv4eL36anlCrkMz8CJ0i/8E/0=";
    
    public static final SecretKey SIGNING_KEY = Jwts.SIG.HS256.key().build();
    
    public static final String BEARER_TYPE = "Bearer";
    
    public static final Context.Key<String> CLIENT_ID_CONTEXT_KEY = Context.key("client-id");
    public static final Context.Key<String> USER_ID_CONTEXT_KEY = Context.key("user-id");
    
    public static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY = Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> CLIENT_ID_METADATA_KEY = Metadata.Key.of("client-id", ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> USER_ID_METADATA_KEY = Metadata.Key.of("user-id", ASCII_STRING_MARSHALLER);
    
    public static final long JWT_TTL = 3600000;
    
}