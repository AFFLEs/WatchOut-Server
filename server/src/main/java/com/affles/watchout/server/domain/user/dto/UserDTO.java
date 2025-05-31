package com.affles.watchout.server.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

public class UserDTO {

    public static class UserRequest {

        @Getter
        public static class SignUpRequest {
            @NotNull private UserInfo userInfo;
            @NotNull private TermsAgreement1 termsAgreement1;
            @NotNull private TermsAgreement2 termsAgreement2;
            @NotNull private TravelInfo travelInfo;

            @Getter
            public static class UserInfo {
                @NotBlank private String name;
                @NotNull private LocalDate birthdate;
                @NotBlank private String email;
                @NotBlank private String password;
                @NotBlank private String confirmPassword;
                @NotBlank private String phoneNumber;
            }

            @Getter
            public static class TermsAgreement1 {
                @NotNull private Boolean agreeEmergencyDataShare;
                @NotNull private Boolean allowLocationTracking;
            }

            @Getter
            public static class TermsAgreement2 {
                @NotNull private Boolean vibrationAlert;
                @NotNull private Boolean enableWatchEmergencySignal;
                @NotBlank private String guardianPhone;
            }

            @Getter
            public static class TravelInfo {
                @NotNull private LocalDate departDate;
                @NotNull private LocalDate arriveDate;
            }
        }

        @Getter
        public static class LoginRequest {
            @NotBlank private String email;
            @NotBlank private String password;
        }
    }

    public static class UserResponse {

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SignUpResponse {
            private UserInfo userInfo;
            private TermsAgreement1 termsAgreement1;
            private TermsAgreement2 termsAgreement2;
            private TravelInfo travelInfo;

            @Builder
            @Getter
            public static class UserInfo {
                private String name;
                private LocalDate birthdate;
                private String email;
                private String phoneNumber;
            }

            @Builder
            @Getter
            public static class TermsAgreement1 {
                private Boolean agreeEmergencyDataShare;
                private Boolean allowLocationTracking;
            }

            @Builder
            @Getter
            public static class TermsAgreement2 {
                private Boolean vibrationAlert;
                private Boolean enableWatchEmergencySignal;
                private String guardianPhone;
            }

            @Builder
            @Getter
            public static class TravelInfo {
                private LocalDate departDate;
                private LocalDate arriveDate;
            }
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

        @Builder
        @Getter
        public static class UserProfileResponse {
            private String name;
            private LocalDate birthdate;
            private String phoneNumber;
            private String guardianPhone;
            private Boolean vibrationAlert;
            private Boolean enableWatchEmergencySignal;
        }
    }

    public static class UserSettingRequest {
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
