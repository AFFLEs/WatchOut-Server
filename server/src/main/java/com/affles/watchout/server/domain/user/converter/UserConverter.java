package com.affles.watchout.server.domain.user.converter;

import com.affles.watchout.server.domain.user.dto.UserDTO.UserRequest.SignUpRequest;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserResponse.SignUpResponse;
import com.affles.watchout.server.domain.user.entity.User;

public class UserConverter {

    public static User toUser(SignUpRequest request, String encodedPassword) {
        return User.builder()
                .name(request.getUserInfo().getName())
                .birthdate(request.getUserInfo().getBirthdate())
                .email(request.getUserInfo().getEmail())
                .password(encodedPassword)
                .phoneNumber(request.getUserInfo().getPhoneNumber())
                .agreeEmergencyDataShare(request.getTermsAgreement1().getAgreeEmergencyDataShare())
                .allowLocationTracking(request.getTermsAgreement1().getAllowLocationTracking())
                .vibrationAlert(request.getTermsAgreement2().getVibrationAlert())
                .enableWatchEmergencySignal(request.getTermsAgreement2().getEnableWatchEmergencySignal())
                .guardianPhone(request.getTermsAgreement2().getGuardianPhone())
                .build();
    }

    public static SignUpResponse toSignUpResponse(User user, SignUpRequest request) {
        return SignUpResponse.builder()
                .userInfo(SignUpResponse.UserInfo.builder()
                        .name(user.getName())
                        .birthdate(user.getBirthdate())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .build())
                .termsAgreement1(SignUpResponse.TermsAgreement1.builder()
                        .agreeEmergencyDataShare(user.getAgreeEmergencyDataShare())
                        .allowLocationTracking(user.getAllowLocationTracking())
                        .build())
                .termsAgreement2(SignUpResponse.TermsAgreement2.builder()
                        .vibrationAlert(user.getVibrationAlert())
                        .enableWatchEmergencySignal(user.getEnableWatchEmergencySignal())
                        .guardianPhone(user.getGuardianPhone())
                        .build())
                .travelInfo(SignUpResponse.TravelInfo.builder()
                        .departDate(request.getTravelInfo().getDepartDate())
                        .arriveDate(request.getTravelInfo().getArriveDate())
                        .build())
                .build();
    }
}