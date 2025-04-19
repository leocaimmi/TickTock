package com.TickTock.TickTock.services;

import com.TickTock.TickTock.data.dtos.request.UserRequest;
import com.TickTock.TickTock.data.dtos.response.UserResponse;
import com.TickTock.TickTock.data.entities.UserEntity;
import com.TickTock.TickTock.data.mappers.UserMapper;
import com.TickTock.TickTock.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, EmailService emailService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.emailService = emailService;
    }

    /**
     * Create a new user.
     *
     * @param userRequest the user request containing user details
     * @return the created user response
     */
    public UserResponse createUser(UserRequest userRequest) {
        return userMapper.toResponse(userRepository.save(userMapper.toEntity(userRequest)));
    }


    /**
     * Notify users with birthdays today by sending birthday emails.
     * This method is scheduled to run every day at midnight.
     *
     * @return true if there are users with birthdays today, false otherwise
     */
    @Scheduled(cron = "0 0 0 * * ?") // Runs every day at midnight
    @Transactional
    public boolean notifyBirthday() {
        int day = LocalDate.now().getDayOfMonth();
        int month = LocalDate.now().getMonthValue();

        // get the list of users with birthday today
        List<UserEntity> birthdayUsers = userRepository.findUsersWithBirthdayToday(day, month);

        // for each user, send a birthday email
        birthdayUsers.forEach(user -> {
            emailService.sendBirthdayEmail(
                    user.getEmail(),
                    user.getUsername(),
                    user.getBornDate()
            );
        });


        return !birthdayUsers.isEmpty();
    }


}
