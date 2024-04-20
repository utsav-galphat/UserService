package org.utech.userservice.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.utech.userservice.dtos.LogOutRequestDto;
import org.utech.userservice.dtos.SignUpRequestDto;
import org.utech.userservice.dtos.ValidateRequestDto;
import org.utech.userservice.model.Token;
import org.utech.userservice.model.User;
import org.utech.userservice.respositoty.TokenRepository;
import org.utech.userservice.respositoty.UserRepository;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository,
                           TokenRepository tokenRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User signUp(SignUpRequestDto user) {
        User u = User.builder()
                .email(user.getEmail())
                .hashedPassword(bCryptPasswordEncoder.encode(user.getPassword()))
                .name(user.getName())
                .isEmailVerified(false)
                .build();
        userRepository.save(u);
        return u;
    }

    @Override
    public User getUser(Long id) {
       return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Token logIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(bCryptPasswordEncoder.matches(password, user.getHashedPassword())){
                Token token = new Token();
                token.setUser(user);
                token.setValue(RandomStringUtils.randomAlphabetic(128));
                LocalDate today = LocalDate.now();
                LocalDate tomorrow = today.plusDays(1);
                Date expiryDate = Date.from(tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant());
                token.setExpiryAt(expiryDate);
                tokenRepository.save(token);
                return token;
            }
            else {
                throw new RuntimeException("Invalid password");
            }
        }
        else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void logOut(LogOutRequestDto user) {
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeletedEquals(user.getToken(),false);
        if(tokenOptional.isPresent()){
            Token token = tokenOptional.get();
            token.setDeleted(true);
            tokenRepository.save(token);
        }
        else {
            throw new RuntimeException("Invalid token");
        }
    }

    @Override
    public void validateToken(ValidateRequestDto validateRequest) throws AccessDeniedException {
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedEquals(validateRequest.getToken(),false);

        if(optionalToken.isPresent()){
            Date expiryTime =  optionalToken.get().getExpiryAt();
            Instant now = Instant.now();
            if(expiryTime.toInstant().isBefore(now))
                throw new AccessDeniedException("Token is invalid");
        }
        else throw new AccessDeniedException("Token is invalid");
    }
}
