package com.donamdong.spiritualwar.endpoint.user.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class SignUpRequest {
    @NotNull
    @NotBlank
    private String userId;

    @NotNull
    @NotBlank
    private String userPassword;

    @NotNull
    @NotBlank
    private String userName;
}
