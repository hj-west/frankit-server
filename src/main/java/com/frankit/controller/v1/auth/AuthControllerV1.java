package com.frankit.controller.v1.auth;

import com.frankit.controller.v1.BaseControllerV1;
import com.frankit.dto.BaseResponse;
import com.frankit.dto.BaseResponseStatus;
import com.frankit.dto.auth.LoginRequestDto;
import com.frankit.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 API", description = "로그인 등의 인증 관련 API")
@RestController
@RequestMapping(AuthControllerV1.PATH_NAME)
@RequiredArgsConstructor
public class AuthControllerV1 {
    public static final String PATH_NAME = BaseControllerV1.PATH_NAME + "/auth"; // 상수 선언

    private final AuthService authService;

    @Operation(summary = "로그인 API", description = "이메일과 비밀번호를 이용하여 로그인하고 JWT 토큰을 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음"),
            @ApiResponse(responseCode = "401", description = "잘못된 ID/Password")
    })
    @PostMapping
    public ResponseEntity<BaseResponse<String>> login(@RequestBody @Valid LoginRequestDto requestDto) {
        try {
            return ResponseEntity.ok().body(new BaseResponse<>(authService.login(requestDto.getEmail(), requestDto.getPassword())));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.FRAN404001));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseResponse<>(BaseResponseStatus.FRAN401001));
        }
    }

}
