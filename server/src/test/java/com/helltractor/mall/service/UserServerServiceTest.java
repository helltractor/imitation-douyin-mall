package com.helltractor.mall.service;

import com.helltractor.mall.config.ServiceConfiguration;
import com.helltractor.mall.entity.UserEntity;
import com.helltractor.mall.handler.TransferEntityHandler;
import com.helltractor.mall.mapper.UserServiceMapper;
import com.helltractor.mall.proto.user.LoginReq;
import com.helltractor.mall.proto.user.LoginResp;
import com.helltractor.mall.proto.user.RegisterReq;
import com.helltractor.mall.proto.user.RegisterResp;

import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static com.helltractor.mall.constant.BaseParamConstant.*;
import static com.helltractor.mall.constant.ModelConstant.USER_ENTITY_TEST;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = {
        "grpc.server.in-process-name=test",
        "grpc.server.port=-1",
        "grpc.client.serviceServer.address=in-process:test"
})
@SpringJUnitConfig(ServiceConfiguration.class)
@DirtiesContext
public class UserServerServiceTest {
    
    @InjectMocks
    private UserServerService userServerService;
    
    @Mock
    private UserServiceMapper userServiceMapper;
    
    @Autowired
    private TransferEntityHandler handler;
    
    public static final UserEntity USER_ENTITY = UserEntity.builder()
            .id(USER_ID)
            .email(EMAIL)
            .password(ENCRYPT_PASSWORD)
            .confirmPassword(CONFIRM_PASSWORD)
            .build();
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.userServerService.handler = handler;
    }
    
    @Test
    void testRegister() {
        doAnswer(invocationOnMock -> {
            UserEntity userEntity = invocationOnMock.getArgument(0);
            userEntity.setId(USER_ID);
            return null;
        }).when(userServiceMapper).insert(any(UserEntity.class));
        
        RegisterReq request = RegisterReq.newBuilder()
                .setEmail(EMAIL)
                .setPassword(PASSWORD)
                .setConfirmPassword(CONFIRM_PASSWORD)
                .build();
        StreamRecorder<RegisterResp> responseObverse = StreamRecorder.create();
        userServerService.register(request, responseObverse);

        assertNull(responseObverse.getError());
        RegisterResp response = responseObverse.getValues().get(0);
        assertEquals(USER_ID, response.getUserId());

        verify(userServiceMapper).insert(any(UserEntity.class));
    }
    
    @Test
    void testRegisterFailure() {
        doAnswer(invocationOnMock -> {
            UserEntity userEntity = invocationOnMock.getArgument(0);
            userEntity.setId(USER_ID_TEST);
            return null;
        }).when(userServiceMapper).insert(any(UserEntity.class));
        
        RegisterReq request = RegisterReq.newBuilder()
                .setEmail(EMAIL)
                .setPassword(PASSWORD)
                .setConfirmPassword(CONFIRM_PASSWORD)
                .build();
        StreamRecorder<RegisterResp> responseObverse = StreamRecorder.create();
        userServerService.register(request, responseObverse);
        
        assertNotNull(responseObverse.getError());
        assertEquals("Email already exists", responseObverse.getError().getMessage());
        
        verify(userServiceMapper).insert(any(UserEntity.class));
    }
    
    @Test
    void testLogin() {
        when(userServiceMapper.searchUserByEmail(any(String.class))).thenReturn(USER_ENTITY);

        LoginReq loginReq = LoginReq.newBuilder().setEmail(EMAIL).setPassword(PASSWORD).build();
        StreamRecorder<LoginResp> responseObverse = StreamRecorder.create();
        userServerService.login(loginReq, responseObverse);

        assertNull(responseObverse.getError());
        LoginResp loginResponse = responseObverse.getValues().get(0);
        assertEquals(USER_ID, loginResponse.getUserId());

        verify(userServiceMapper).searchUserByEmail(any(String.class));
    }
    
    @Test
    void testLoginUserNotFound() {
        when(userServiceMapper.searchUserByEmail(any(String.class))).thenReturn(USER_ENTITY_TEST);
        
        LoginReq loginReq = LoginReq.newBuilder().setEmail(EMAIL).setPassword(PASSWORD).build();
        StreamRecorder<LoginResp> responseObverse = StreamRecorder.create();
        userServerService.login(loginReq, responseObverse);
        
        assertNotNull(responseObverse.getError());
        assertEquals("User not found", responseObverse.getError().getMessage());
        
        verify(userServiceMapper).searchUserByEmail(any(String.class));
    }
    
    @Test
    void testLoginPasswordIncorrect() {
        when(userServiceMapper.searchUserByEmail(any(String.class))).thenReturn(USER_ENTITY);
        
        LoginReq loginReq = LoginReq.newBuilder().setEmail(EMAIL).setPassword(ENCRYPT_PASSWORD).build();
        StreamRecorder<LoginResp> responseObverse = StreamRecorder.create();
        userServerService.login(loginReq, responseObverse);
        
        assertNotNull(responseObverse.getError());
        assertEquals("Password is incorrect", responseObverse.getError().getMessage());
        
        verify(userServiceMapper).searchUserByEmail(any(String.class));
    }
    
}