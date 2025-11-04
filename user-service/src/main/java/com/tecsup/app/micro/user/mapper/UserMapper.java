package com.tecsup.app.micro.user.mapper;

import com.tecsup.app.micro.user.dto.User;
import com.tecsup.app.micro.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User toDomain(UserEntity entity);
    UserEntity toEntity(User domain);
    List<User> toDomain(List<UserEntity> entities);

}
