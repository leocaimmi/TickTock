package com.TickTock.TickTock.birthday.application.mappers;

import com.TickTock.TickTock.birthday.application.dtos.response.BirthdayResponse;
import com.TickTock.TickTock.birthday.domain.entities.BirthdayEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BirthdayMapperMapStruct {

    @Mapping(source = "userEntity.id", target = "userId")
    BirthdayResponse toResponse(BirthdayEntity birthday);

    List<BirthdayResponse> toResponseList(List<BirthdayEntity> entities);


}
