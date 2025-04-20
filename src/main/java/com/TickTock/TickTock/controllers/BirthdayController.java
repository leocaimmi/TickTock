package com.TickTock.TickTock.controllers;


import com.TickTock.TickTock.data.dtos.request.BirthdayRequest;
import com.TickTock.TickTock.services.BirthdayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/birthdays")
@Tag(name = "Birthday", description = "Birthday management API")
public class BirthdayController {

    private final BirthdayService birthdayService;

    /**
     * Constructor for BirthdayController.
     *
     * @param birthdayService the birthday service
     */
    @Autowired
    public BirthdayController(BirthdayService birthdayService) {
        this.birthdayService = birthdayService;
    }

    @Operation(
            summary = "Add a birthday to a user",
            description = "This endpoint allows you to associate a birth date to a user by providing the user's ID and birth date in the request body."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Birthday successfully added to user",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request, invalid data",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping
    public ResponseEntity<Boolean> addBirthdayToUser(@Valid @RequestBody BirthdayRequest birthdayRequest) {
        return ResponseEntity.ok(birthdayService.createBirthday(birthdayRequest));
    }
}
