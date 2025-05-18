package com.affles.watchout.server.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

public class UserDTO {

    public static class UserRequest {

        @Getter
        public static class SignUpRequest {
            @NotBlank
            private String name;

            @NotNull
            private LocalDate birthdate;

            @NotBlank
            private String email;

            @NotBlank
            private String password;

            @NotBlank
            private String confirmPassword;

            @NotBlank
            private String phoneNumber;
        }

        @Getter
        public static class LoginRequest {
            @NotBlank
            private String email;

            @NotBlank
            private String password;
        }
    }

    public static class UserResponse {

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SignUpResponse {
            private Long userId;
            private String name;
            private String email;
        }

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class LoginResponse {
            private String accessToken;
            private String refreshToken;
        }
    }
}
