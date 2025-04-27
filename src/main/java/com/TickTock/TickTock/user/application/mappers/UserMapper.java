package com.TickTock.TickTock.user.application.mappers;

import com.TickTock.TickTock.birthday.application.mappers.BirthdayMapper;
import com.TickTock.TickTock.user.application.dtos.request.UserRequest;
import com.TickTock.TickTock.user.application.dtos.response.UserResponse;
import com.TickTock.TickTock.user.domain.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    private final BirthdayMapper birthdayMapper;

    @Autowired
    public UserMapper(BirthdayMapper birthdayMapper) {
        this.birthdayMapper = birthdayMapper;
    }

    public UserResponse toResponse(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .status(user.getStatus())
                .bornDate(user.getBornDate())
                .birthdayResponseList(birthdayMapper.toModelList(user.getBirthdayEntityList()))//todo agregar cuando exista
                .build();
    }

    public List<UserResponse> toModelList(List<UserEntity> userEntities) {
        if (userEntities == null) {
            return List.of();
        }
        return userEntities.stream()
                .map(this::toResponse)
                .toList();
    }


    //post user
    public UserEntity toEntity(UserRequest user) {
        return UserEntity.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .bornDate(user.getBornDate())
                .status(true)
                .birthdayEntityList(new ArrayList<>())
                .build();
    }

}
