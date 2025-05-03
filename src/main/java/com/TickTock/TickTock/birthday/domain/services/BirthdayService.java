package com.TickTock.TickTock.birthday.domain.services;

import com.TickTock.TickTock.birthday.application.dtos.request.BirthdayRequest;
import com.TickTock.TickTock.birthday.application.dtos.response.BirthdayResponse;
import com.TickTock.TickTock.birthday.domain.entities.BirthdayEntity;
import com.TickTock.TickTock.user.domain.entities.UserEntity;
import com.TickTock.TickTock.birthday.application.mappers.BirthdayMapper;
import com.TickTock.TickTock.birthday.domain.repositories.BirthdayRepository;
import com.TickTock.TickTock.user.domain.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<BirthdayResponse> getBirthdaysByUserId(Long userId, Pageable pageable) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User "+userId+" not found"));

        Page<BirthdayEntity> birthdayPage = birthdayRepository.findByUserEntity(user, pageable);
        if (birthdayPage.isEmpty()) {
            throw new EntityNotFoundException("No birthdays found for user " + userId);
        }

        return birthdayMapper.toModelPage(birthdayPage);
    }
}
