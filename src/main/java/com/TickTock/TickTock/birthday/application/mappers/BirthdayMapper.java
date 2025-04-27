package com.TickTock.TickTock.birthday.application.mappers;

import com.TickTock.TickTock.birthday.application.dtos.request.BirthdayRequest;
import com.TickTock.TickTock.birthday.application.dtos.response.BirthdayResponse;
import com.TickTock.TickTock.birthday.domain.entities.BirthdayEntity;
import com.TickTock.TickTock.user.domain.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Component
public class BirthdayMapper {


    public BirthdayEntity toEntity(BirthdayRequest birthdayRequest, UserEntity userEntity) {

        LocalDate birthDate = birthdayRequest.getBornDate();
        LocalDate today = LocalDate.now();

        int age = Period.between(birthDate, today).getYears();// age -1 because we are going to add 1 if the birthday is today

        if(birthDate.getYear() == LocalDate.now().getYear() &&
                birthDate.getMonthValue() == LocalDate.now().getMonthValue() &&
                birthDate.getDayOfMonth() == LocalDate.now().getDayOfMonth()){
            age += 1; // add 1 if the birthday is today

        }
        return BirthdayEntity.builder()
                .name(birthdayRequest.getName())
                .birthDate(birthdayRequest.getBornDate())
                .turningAge(age)//current age
                .status(true) // default status
                .userEntity(userEntity)
                .build();

    }

    public BirthdayResponse toModel(BirthdayEntity birthdayEntity) {
        return BirthdayResponse.builder()
                .id(birthdayEntity.getId())
                .name(birthdayEntity.getName())
                .birthDate(birthdayEntity.getBirthDate())
                .turningAge(birthdayEntity.getTurningAge())
                .status(birthdayEntity.getStatus())
                .userId(birthdayEntity.getUserEntity().getId())
                .build();
    }

    public List<BirthdayResponse> toModelList(List<BirthdayEntity> birthdayEntities) {
        if (birthdayEntities == null) {
            return List.of();
        }
        return birthdayEntities.stream()
                .map(this::toModel)
                .toList();
    }
}
