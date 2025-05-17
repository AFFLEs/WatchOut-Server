package com.affles.watchout.server.domain.user.converter;

import com.affles.watchout.server.domain.user.dto.UserDTO.UserRequest.SignUpRequest;
import com.affles.watchout.server.domain.user.dto.UserDTO.UserResponse.SignUpResponse;
import com.affles.watchout.server.domain.user.entity.User;

public class UserConverter {

    public static User toUser(SignUpRequest request, String encodedPassword) {
        return User.builder()
                .name(request.getName())
                .birthdate(request.getBirthdate())
                .email(request.getEmail())
                .password(encodedPassword)
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    public static SignUpResponse toSignUpResponse(User user) {
        return SignUpResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}