package org.cristianvelasquezp.springsecuritypractice.mappers;

import org.cristianvelasquezp.springsecuritypractice.entities.UserEntity;
import org.cristianvelasquezp.springsecuritypractice.models.UserRequest;
import org.cristianvelasquezp.springsecuritypractice.models.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    UserEntity userRequestToUserEntity(UserRequest userRequest);

    UserResponse userEntityToResponse(UserEntity userEntity);
}
