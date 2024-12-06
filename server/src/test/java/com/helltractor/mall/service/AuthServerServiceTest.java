package com.helltractor.mall.service;

import com.helltractor.mall.config.ServiceConfiguration;
import com.helltractor.mall.constant.JwtClaimConstant;
import com.helltractor.mall.proto.auth.DeliverTokenReq;
import com.helltractor.mall.proto.auth.DeliveryResp;
import com.helltractor.mall.proto.auth.VerifyResp;
import com.helltractor.mall.proto.auth.VerifyTokenReq;
import com.helltractor.mall.utils.JwtUtil;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static com.helltractor.mall.constant.BaseParamConstant.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "grpc.server.in-process-name=test",
        "grpc.server.port=-1",
        "grpc.client.serviceServer.address=in-process:test"
})
@SpringJUnitConfig(classes = ServiceConfiguration.class)
@DirtiesContext
public class AuthServerServiceTest {
    
    @Autowired
    private AuthServerService authService;
    
    @Test
    void testDeliverTokenByRPC() {
        DeliverTokenReq request = DeliverTokenReq.newBuilder()
                .setUserId(USER_ID)
                .build();
        StreamRecorder<DeliveryResp> responseObverse = StreamRecorder.create();
        authService.deliverTokenByRPC(request, responseObverse);
        
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
        authService.verifyTokenByRPC(request, responseObverse);
        
        assertNull(responseObverse.getError());
        assertTrue(responseObverse.getValues().get(0).getRes());
    }
    
    @Test
    void testVerifyTokenByRPCExpired() {
        VerifyTokenReq request = VerifyTokenReq.newBuilder()
                .setToken(TOKEN_EXPIRED)
                .build();
        StreamRecorder<VerifyResp> responseObverse = StreamRecorder.create();
        authService.verifyTokenByRPC(request, responseObverse);
        
        assertNull(responseObverse.getError());
        assertFalse(responseObverse.getValues().get(0).getRes());
    }
    
}