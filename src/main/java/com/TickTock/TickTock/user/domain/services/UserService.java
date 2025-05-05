package com.TickTock.TickTock.user.domain.services;

import com.TickTock.TickTock.user.application.dtos.request.UserRequest;
import com.TickTock.TickTock.birthday.application.dtos.response.BirthdayResponse;
import com.TickTock.TickTock.user.application.dtos.response.UserResponse;
import com.TickTock.TickTock.user.application.mappers.UserMapperMapStruct;
import com.TickTock.TickTock.user.domain.entities.UserEntity;
import com.TickTock.TickTock.birthday.application.mappers.BirthdayMapper;
import com.TickTock.TickTock.user.application.mappers.UserMapper;
import com.TickTock.TickTock.birthday.domain.repositories.BirthdayRepository;
import com.TickTock.TickTock.user.domain.repositories.UserRepository;
import com.TickTock.TickTock.shared.infrastructure.notifications.EmailService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserMapperMapStruct userMapperMapStruct;
    private final BirthdayMapper birthdayMapper;
    private final EmailService emailService;
    private final BirthdayRepository birthdayRepository;
    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, UserMapperMapStruct userMapperMapStruct, BirthdayMapper birthdayMapper, EmailService emailService, BirthdayRepository birthdayRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userMapperMapStruct = userMapperMapStruct;
        this.birthdayMapper = birthdayMapper;
        this.emailService = emailService;
        this.birthdayRepository = birthdayRepository;
    }

    /**
     * Create a new user.
     *
     * @param userRequest the user request containing user details
     * @return the created user response
     */
    public UserResponse createUser(UserRequest userRequest) {
        return userMapperMapStruct.toResponse(userRepository.save(userMapperMapStruct.toEntity(userRequest)));
    }

    /**
     * Get a list of users with birthdays in the current month and send birthday emails.
     * This method is scheduled to run on the first day of each month at midnight.
     *
     * @param userId the ID of the user to send birthday emails to
     * @return a list of birthday responses for the specified user
     */
    //@Scheduled(cron = "0 0 0 1 * ?") // A las 00:00 del día 1 de cada mes
    @Transactional
    public List<BirthdayResponse> getBirthdayUsers(Long userId) {
        int month = LocalDate.now().getMonthValue();

        List<BirthdayResponse> birthdayList = birthdayMapper
                .toModelList(birthdayRepository.findBirthdaysGroupedByUserInMonth(month));

        Map<Long, List<BirthdayResponse>> grouped = birthdayList.stream()
                .collect(Collectors.groupingBy(BirthdayResponse::getUserId));

        grouped.entrySet()
                .forEach(entry -> {

                    UserEntity user = userRepository.findById(entry.getKey())
                            .orElseThrow(() -> new EntityNotFoundException("User "+entry.getKey()+" not found"));

                    List<BirthdayResponse> birthdayResponses = entry.getValue();
                    try {
                        emailService.sendBirthdayListEmail(user.getEmail(), user.getUsername(), birthdayResponses);
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                });



        return grouped.get(userId);
    }
    /**
     * Notify users with birthdays today by sending birthday emails.
     * This method is scheduled to run every day at midnight.
     *
     * @return true if there are users with birthdays today, false otherwise
     */
    @Scheduled(cron = "0 0 0 * * ?") // Every day at midnight
    @Transactional
    public boolean notifyUserBirthday() {
        int day = LocalDate.now().getDayOfMonth();
        int month = LocalDate.now().getMonthValue();

        // get the list of users with birthday today
        List<UserEntity> birthdayUsers = userRepository.findUsersWithBirthdayToday(day, month);

        // for each user, send a birthday email
        birthdayUsers.forEach(user -> {
            try {
                System.out.println(user);
                emailService.sendBirthdayEmailUser(
                        user.getEmail(),
                        user.getUsername(),
                        user.getBornDate()
                );

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });


        return !birthdayUsers.isEmpty();
    }

    /**
     * Get all users.
     *
     * @return a list of user responses
     */
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userMapperMapStruct
                .toModelPage(userRepository.findAll(pageable));
    }

    /**
     * Get a user by ID.
     *
     * @param id the user ID
     * @return the user response
     */
    public UserResponse getUserById(Long id) {
        return userMapper.toResponse(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User "+id+" not found")));
    }
}
