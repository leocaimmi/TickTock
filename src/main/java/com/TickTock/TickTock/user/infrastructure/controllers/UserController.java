package com.TickTock.TickTock.user.infrastructure.controllers;

import com.TickTock.TickTock.user.application.dtos.request.UserRequest;
import com.TickTock.TickTock.birthday.application.dtos.response.BirthdayResponse;
import com.TickTock.TickTock.user.application.dtos.response.UserResponse;
import com.TickTock.TickTock.user.domain.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "User management API")
public class UserController {

    private final UserService userService;

    /**
     * Constructor for UserController.
     *
     * @param userService the user service
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Create a new user",
            description = "This endpoint allows you to create a new user by providing user data in the request body."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request, invalid user data",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(
            summary = "Get all users",
            description = "This endpoint returns a list of all registered users in the system."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of users successfully retrieved",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort)
    {


        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }


    @Operation(
            summary = "Get user information",
            description = "This endpoint retrieves the details of a specific user by providing their ID in the URL."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User found and successfully returned",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request, unable to process the request",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found, no user exists with the given ID",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Server error, unable to complete the request",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(
            summary = "Get user's birthday list",
            description = "[Test first, later it will be automatic each month] This endpoint returns the birthday list of a specific user. The user ID is provided as a query parameter."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Birthday list successfully retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BirthdayResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request, unable to process the request",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Server error, unable to complete the request",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/{id}/birthdays")
    public ResponseEntity<List<BirthdayResponse>> getBirthdayUsers(@PathVariable Long id) { // Not using Pageable because this method is for testing.
        return ResponseEntity.ok(userService.getBirthdayUsers(id));
    }

}
