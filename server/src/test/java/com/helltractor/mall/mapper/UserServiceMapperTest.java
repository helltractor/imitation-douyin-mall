package com.helltractor.mall.mapper;

import com.helltractor.mall.config.AbstractIntegrationTest;
import com.helltractor.mall.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static com.helltractor.mall.constant.BaseParamConstant.EMAIL;
import static com.helltractor.mall.constant.ModelConstant.USER_ENTITY;
import static com.helltractor.mall.constant.ModelConstant.USER_ENTITY_RANDOM;
import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceMapperTest extends AbstractIntegrationTest {
    
    @Autowired
    UserServiceMapper userServiceMapper;
    
    @Test
    void testInsert() {
        userServiceMapper.insert(USER_ENTITY);
        
        assertNotNull(USER_ENTITY.getId());
    }
    
    @Test
    void testSearchUserByEmail() {
        userServiceMapper.insert(USER_ENTITY);
        UserEntity newUserEntity = userServiceMapper.searchUserByEmail(USER_ENTITY.getEmail());
        
        assertEquals(USER_ENTITY.getPassword(), newUserEntity.getPassword());
        assertEquals(USER_ENTITY.getConfirmPassword(), newUserEntity.getConfirmPassword());
    }
    
    @Test
    void testUpdate() {
        userServiceMapper.insert(USER_ENTITY);
        USER_ENTITY_RANDOM.setId(USER_ENTITY.getId());
        
        userServiceMapper.update(USER_ENTITY_RANDOM);
        UserEntity updatedUserEntity = userServiceMapper.searchUserById(USER_ENTITY_RANDOM.getId());
        
        assertNotNull(updatedUserEntity);
        assertEquals(USER_ENTITY_RANDOM.getEmail(), updatedUserEntity.getEmail());
    }
    
    @Test
    void testDelete() {
        userServiceMapper.insert(USER_ENTITY);
        UserEntity newUserEntity = userServiceMapper.searchUserByEmail(EMAIL);
        
        assertNotNull(newUserEntity);
        
        userServiceMapper.delete(USER_ENTITY.getId());
        UserEntity deletedUserEntity = userServiceMapper.searchUserById(USER_ENTITY.getId());
        
        assertNull(deletedUserEntity);
    }
    
}