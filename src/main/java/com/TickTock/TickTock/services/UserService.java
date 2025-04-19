package com.TickTock.TickTock.services;

import com.TickTock.TickTock.data.dtos.request.UserRequest;
import com.TickTock.TickTock.data.dtos.response.UserResponse;
import com.TickTock.TickTock.data.mappers.UserMapper;
import com.TickTock.TickTock.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
}
