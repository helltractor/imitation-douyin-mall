package com.helltractor.mall.mapper;

import com.helltractor.mall.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserServiceMapper {
    
    void insert(UserEntity userEntity);
    
    int searchUserId(String email);
    
    UserEntity searchUserById(int userId);
    
    UserEntity searchUserByEmail(String email);
    
    void update(UserEntity userEntity);
    
    void delete(int userId);
    
}