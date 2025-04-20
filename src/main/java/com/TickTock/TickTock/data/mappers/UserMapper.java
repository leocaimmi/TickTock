package com.TickTock.TickTock.data.mappers;

import com.TickTock.TickTock.data.dtos.request.UserRequest;
import com.TickTock.TickTock.data.dtos.response.UserResponse;
import com.TickTock.TickTock.data.entities.UserEntity;
import org.apache.catalina.User;
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
