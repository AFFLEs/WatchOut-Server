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

        @Builder
        @Getter
        public static class UserSettingResponse {
            private Boolean agreeEmergencyDataShare;
            private Boolean allowLocationTracking;
            private Boolean vibrationAlert;
            private Boolean enableWatchEmergencySignal;
            private String guardianPhone;
        }

        @Builder
        @Getter
        public static class ConsentResponse {
            private Boolean agreeEmergencyDataShare;
            private Boolean allowLocationTracking;
        }

        @Builder
        @Getter
        public static class EmergencyConsentResponse {
            private Boolean agreeEmergencyDataShare;
        }

        @Builder
        @Getter
        public static class LocationConsentResponse {
            private Boolean allowLocationTracking;
        }

        @Builder
        @Getter
        public static class AlertResponse {
            private Boolean vibrationAlert;
            private Boolean enableWatchEmergencySignal;
            private String guardianPhone;
        }

        @Builder
        @Getter
        public static class VibrationResponse {
            private Boolean vibrationAlert;
        }

        @Builder
        @Getter
        public static class WatchEmergencyResponse {
            private Boolean enableWatchEmergencySignal;
        }

        @Builder
        @Getter
        public static class GuardianPhoneResponse {
            private String guardianPhone;
        }
    }

    public static class UserSettingRequest {

        @Getter
        @Setter
        public static class ConsentSettingRequest {
            private Boolean agreeEmergencyDataShare;
            private Boolean allowLocationTracking;
        }

        @Builder
        @Getter
        public static class EmergencyConsentRequest {
            private Boolean agreeEmergencyDataShare;
        }

        @Builder
        @Getter
        public static class LocationConsentRequest {
            private Boolean allowLocationTracking;
        }

        @Getter
        @Setter
        public static class AlertSettingRequest {
            private Boolean vibrationAlert;
            private Boolean enableWatchEmergencySignal;
            private String guardianPhone;
        }

        @Builder
        @Getter
        public static class VibrationRequest {
            private Boolean vibrationAlert;
        }

        @Builder
        @Getter
        public static class WatchEmergencyRequest {
            private Boolean enableWatchEmergencySignal;
        }

        @Builder
        @Getter
        public static class GuardianPhoneRequest {
            private String guardianPhone;
        }
    }
}
