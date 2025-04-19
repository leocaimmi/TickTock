package com.TickTock.TickTock.data.mappers;

import com.TickTock.TickTock.data.dtos.request.UserRequest;
import com.TickTock.TickTock.data.dtos.response.UserResponse;
import com.TickTock.TickTock.data.entities.UserEntity;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserMapper {


    public UserResponse toResponse(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .status(user.getStatus())
                .bornDate(user.getBornDate())
                .birthdayResponseList(null)//todo agregar cuando exista
                .build();
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
