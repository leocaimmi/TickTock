package com.TickTock.TickTock.user.application.mappers;

import com.TickTock.TickTock.birthday.application.mappers.BirthdayMapper;
import com.TickTock.TickTock.shared.infrastructure.utils.Role;
import com.TickTock.TickTock.user.application.dtos.request.UserRequest;
import com.TickTock.TickTock.user.application.dtos.response.UserResponse;
import com.TickTock.TickTock.user.domain.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.TickTock.TickTock.security.domain.entities.CredentialEntity;

@Component
public class UserMapper {
    private final BirthdayMapper birthdayMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(BirthdayMapper birthdayMapper, PasswordEncoder passwordEncoder) {
        this.birthdayMapper = birthdayMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse toResponse(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .bornDate(user.getBornDate())
                .email(user.getCredential().getEmail())
                .status(user.getStatus())
                .birthdayResponseList(birthdayMapper.toModelList(user.getBirthdayEntityList()))
                .role(user.getCredential().getRole())
                .build();
    }

    public UserEntity toEntity(UserRequest request) {
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .bornDate(request.getBornDate())
                .status(true)
                .birthdayEntityList(new ArrayList<>())
                .build();

        CredentialEntity credential = CredentialEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : Role.USER)
                .user(user)
                .build();

        user.setCredential(credential);
        
        return user;
    }

    public List<UserResponse> toModelList(List<UserEntity> userEntities) {
        if (userEntities == null) {
            return List.of();
        }
        return userEntities.stream()
                .map(this::toResponse)
                .toList();
    }


    //Pageable
    public Page<UserResponse> toModelPage(Page<UserEntity> userEntities) {
        if (userEntities == null) {
            return Page.empty();
        }
        return userEntities.map(this::toResponse);
    }

}