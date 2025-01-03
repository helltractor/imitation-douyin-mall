package com.helltractor.mall.service;

import com.helltractor.mall.config.AbstractIntegrationTest;
import com.helltractor.mall.config.ServiceTestConfiguration;
import com.helltractor.mall.entity.UserEntity;
import com.helltractor.mall.handler.TransferEntityHandler;
import com.helltractor.mall.mapper.UserServiceMapper;
import com.helltractor.mall.proto.user.LoginReq;
import com.helltractor.mall.proto.user.LoginResp;
import com.helltractor.mall.proto.user.RegisterReq;
import com.helltractor.mall.proto.user.RegisterResp;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static com.helltractor.mall.constant.BaseParamConstant.*;
import static com.helltractor.mall.constant.ModelConstant.USER_ENTITY_TEST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@SpringJUnitConfig(ServiceTestConfiguration.class)
@DirtiesContext
public class UserServerServiceTest extends AbstractIntegrationTest {
    
    public static final UserEntity USER_ENTITY = UserEntity.builder()
            .id(USER_ID)
            .email(EMAIL)
            .password(ENCRYPT_PASSWORD)
            .confirmPassword(CONFIRM_PASSWORD)
            .build();
    @InjectMocks
    private UserServerService userServerService;
    @Mock
    private UserServiceMapper userServiceMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private TransferEntityHandler handler;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.userServerService.redisTemplate = redisTemplate;
        this.userServerService.handler = handler;
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }
    
    @Test
    void testRegister() {
        when(userServiceMapper.searchUserByEmail(any(String.class))).thenReturn(null);
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
        
        verify(userServiceMapper).searchUserByEmail(any(String.class));
        verify(userServiceMapper).insert(any(UserEntity.class));
    }
    
    @Test
    void testRegisterFailure() {
        when(userServiceMapper.searchUserByEmail(any(String.class))).thenReturn(USER_ENTITY);

        RegisterReq request = RegisterReq.newBuilder()
                .setEmail(EMAIL)
                .setPassword(PASSWORD)
                .setConfirmPassword(CONFIRM_PASSWORD)
                .build();
        StreamRecorder<RegisterResp> responseObverse = StreamRecorder.create();
        userServerService.register(request, responseObverse);
        
        assertNotNull(responseObverse.getError());
        assertEquals("Email already exists", responseObverse.getError().getMessage());
        
        verify(userServiceMapper).searchUserByEmail(any(String.class));
        verify(userServiceMapper, times(0)).insert(any(UserEntity.class));
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
    void testLoginTwice() {
        when(userServiceMapper.searchUserByEmail(any(String.class))).thenReturn(USER_ENTITY);
        
        LoginReq loginReq = LoginReq.newBuilder().setEmail(EMAIL).setPassword(PASSWORD).build();
        StreamRecorder<LoginResp> responseObverse = StreamRecorder.create();
        userServerService.login(loginReq, responseObverse);
        verify(userServiceMapper).searchUserByEmail(any(String.class));
        
        userServerService.login(loginReq, responseObverse);
        verify(userServiceMapper, times(1)).searchUserByEmail(any(String.class));
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