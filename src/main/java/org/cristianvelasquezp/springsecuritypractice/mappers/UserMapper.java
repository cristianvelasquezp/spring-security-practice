package org.cristianvelasquezp.springsecuritypractice.mappers;

import org.cristianvelasquezp.springsecuritypractice.entities.UserEntity;
import org.cristianvelasquezp.springsecuritypractice.models.UserRequest;
import org.cristianvelasquezp.springsecuritypractice.models.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity userRequestToUserEntity(UserRequest userRequest);

    UserResponse userEntityToResponse(UserEntity userEntity);
}
