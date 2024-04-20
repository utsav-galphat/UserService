package org.utech.userservice.controller;

import org.springframework.web.bind.annotation.*;
import org.utech.userservice.dtos.*;
import org.utech.userservice.model.Token;
import org.utech.userservice.model.User;
import org.utech.userservice.service.UserService;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/users")
public class UserServiceController {

    final private UserService userService;

    public UserServiceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public SignUpResponseDto getUser(@PathVariable("id") Long id) {
        User user =  userService.getUser(id);
        return SignUpResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .isEmailVerified(user.isEmailVerified())
                .build();
    }

    @PostMapping("/signup")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto signUpRequest) {
        User user =  userService.signUp(signUpRequest);
        return SignUpResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .isEmailVerified(user.isEmailVerified())
                .build();
    }

    @PostMapping("/login")
    public Token logIn(@RequestBody LogInRequestDto requestDto) {
        return userService.logIn(requestDto.getEmail(), requestDto.getPassword());
    }

    @PostMapping("/validate")
    public void validateUser(@RequestBody ValidateRequestDto validateRequest) throws AccessDeniedException {
        userService.validateToken(validateRequest);
    }

    @PostMapping("/logout")
    public void logOut(@RequestBody LogOutRequestDto requestDto) {
        userService.logOut(requestDto);
    }


}
