package com.affles.watchout.server.domain.spot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

public class SpotDTO {

    public static class SpotRequest {

        @Getter
        public static class CreateSpotRequest {
            @NotNull(message = "방문 날짜는 필수입니다.")
            private LocalDate spotDate;

            private LocalTime spotTime;

            @NotBlank(message = "장소명은 필수입니다.")
            private String spotName;

            @NotBlank(message = "장소 설명은 필수입니다.")
            private String spotDetail;

            private Double latitude;
            private Double longitude;

            private String city;
            private String country;
        }
    }

    public static class SpotResponse {

        @Builder
        @Getter
        public static class SpotInfo {
            private Long spotId;
            private LocalDate spotDate;
            private LocalTime spotTime;
            private String spotName;
            private String spotDetail;
            private Double latitude;
            private Double longitude;
            private String city;
            private String country;
        }

        @Builder
        @Getter
        public static class DeletedSpotInfo {
            private Long spotId;
            private String spotName;
        }
    }
}