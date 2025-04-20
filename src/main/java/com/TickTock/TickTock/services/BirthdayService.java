package com.TickTock.TickTock.services;

import com.TickTock.TickTock.data.dtos.request.BirthdayRequest;
import com.TickTock.TickTock.data.entities.BirthdayEntity;
import com.TickTock.TickTock.data.entities.UserEntity;
import com.TickTock.TickTock.data.mappers.BirthdayMapper;
import com.TickTock.TickTock.data.repositories.BirthdayRepository;
import com.TickTock.TickTock.data.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BirthdayService {

    private final BirthdayRepository birthdayRepository;
    private final UserRepository userRepository;
    private final BirthdayMapper birthdayMapper;
    @Autowired
    public BirthdayService(BirthdayRepository birthdayRepository, UserRepository userRepository, BirthdayMapper birthdayMapper) {
        this.birthdayRepository = birthdayRepository;
        this.userRepository = userRepository;
        this.birthdayMapper = birthdayMapper;
    }

    public boolean createBirthday(BirthdayRequest birthdayRequest) {
        boolean rta = false;

        UserEntity user = userRepository.findById(birthdayRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User "+birthdayRequest.getUserId()+" not found"));

        BirthdayEntity newBirthday = birthdayMapper.toEntity(birthdayRequest,user);
        newBirthday = birthdayRepository.save(newBirthday);
        // Add the new birthday to the user's birthday list
        user.getBirthdayEntityList().add(newBirthday);
        userRepository.save(user);// save the user with the new birthday
        rta = true;
        return rta;
    }

}
