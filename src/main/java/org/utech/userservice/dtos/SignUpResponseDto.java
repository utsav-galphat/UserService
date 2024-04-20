package org.utech.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class SignUpResponseDto {
    private String name;
    private String email;
    private boolean isEmailVerified;
}
