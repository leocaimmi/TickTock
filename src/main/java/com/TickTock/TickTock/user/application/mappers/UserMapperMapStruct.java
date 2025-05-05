package com.TickTock.TickTock.user.application.mappers;

import com.TickTock.TickTock.birthday.application.mappers.BirthdayMapperMapStruct;
import com.TickTock.TickTock.user.application.dtos.request.UserRequest;
import com.TickTock.TickTock.user.application.dtos.response.UserResponse;
import com.TickTock.TickTock.user.domain.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = "spring",uses = {BirthdayMapperMapStruct.class})
public interface UserMapperMapStruct {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "true") // Set default value for status
    @Mapping(target = "birthdayEntityList", expression = "java(new ArrayList<>())")
    UserEntity toEntity(UserRequest user);

    @Mapping(source = "birthdayEntityList", target = "birthdayResponseList")
    UserResponse toResponse(UserEntity user);

    @Mapping(source = "birthdayEntityList", target = "birthdayResponseList")
    List<UserResponse> toModelList(List<UserEntity> userEntities);

    default Page<UserResponse> toModelPage(Page<UserEntity> userEntities) {
        return userEntities.map(this::toResponse);
    }
}
