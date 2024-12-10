package com.helltractor.mall.service;

import com.helltractor.mall.config.ServiceTestConfiguration;
import com.helltractor.mall.constant.JwtClaimConstant;
import com.helltractor.mall.proto.auth.DeliverTokenReq;
import com.helltractor.mall.proto.auth.DeliveryResp;
import com.helltractor.mall.proto.auth.VerifyResp;
import com.helltractor.mall.proto.auth.VerifyTokenReq;
import com.helltractor.mall.util.JwtUtil;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static com.helltractor.mall.constant.BaseParamConstant.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringJUnitConfig(classes = ServiceTestConfiguration.class)
@ActiveProfiles("test")
@DirtiesContext
public class AuthServerServiceTest {
    
    AuthServerService authServerService = new AuthServerService();
    
    @Test
    void testDeliverTokenByRPC() {
        DeliverTokenReq request = DeliverTokenReq.newBuilder()
                .setUserId(USER_ID)
                .build();
        StreamRecorder<DeliveryResp> responseObverse = StreamRecorder.create();
        authServerService.deliverTokenByRPC(request, responseObverse);
        
        assertNull(responseObverse.getError());
        String token = responseObverse.getValues().get(0).getToken();
        assertEquals(USER_ID, JwtUtil.parseJWT(token).getPayload().get(JwtClaimConstant.USER_ID));
    }
    
    @Test
    void testVerifyTokenByRPC() {
        VerifyTokenReq request = VerifyTokenReq.newBuilder()
                .setToken(TOKEN)
                .build();
        StreamRecorder<VerifyResp> responseObverse = StreamRecorder.create();
        authServerService.verifyTokenByRPC(request, responseObverse);
        
        assertNull(responseObverse.getError());
        assertTrue(responseObverse.getValues().get(0).getRes());
    }
    
    @Test
    void testVerifyTokenByRPCExpired() {
        VerifyTokenReq request = VerifyTokenReq.newBuilder()
                .setToken(TOKEN_EXPIRED)
                .build();
        StreamRecorder<VerifyResp> responseObverse = StreamRecorder.create();
        authServerService.verifyTokenByRPC(request, responseObverse);
        
        assertNull(responseObverse.getError());
        assertFalse(responseObverse.getValues().get(0).getRes());
    }
    
}