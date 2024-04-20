package org.utech.userservice.service;

import org.utech.userservice.dtos.LogOutRequestDto;
import org.utech.userservice.dtos.SignUpRequestDto;
import org.utech.userservice.dtos.ValidateRequestDto;
import org.utech.userservice.model.Token;
import org.utech.userservice.model.User;

import java.nio.file.AccessDeniedException;

public interface UserService {
    public User signUp(SignUpRequestDto user);

    public User getUser(Long id);
    public Token logIn(String email, String password);
    public void logOut(LogOutRequestDto user);


    void validateToken(ValidateRequestDto validateRequest) throws AccessDeniedException;
}
