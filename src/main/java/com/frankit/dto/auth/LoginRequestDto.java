package com.frankit.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequestDto {
    @Schema(description = "사용자의 이메일", example = "user1@mail.com")
    @NotNull
    @NotBlank
    @Email
    private String email;

    @Schema(description = "사용자의 패스워드", example = "user1")
    @NotNull
    @NotBlank
    private String password;
}
