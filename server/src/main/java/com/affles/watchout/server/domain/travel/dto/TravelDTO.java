package com.affles.watchout.server.domain.travel.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

public class TravelDTO {

    public static class TravelRequest {

        @Getter
        public static class CreateTravelRequest {
            @NotNull
            private LocalDate departDate;

            @NotNull
            private LocalDate arriveDate;
        }
    }

    public static class TravelResponse {

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class TravelInfoResponse {
            private Long travelId;
            private LocalDate departDate;
            private LocalDate arriveDate;
        }
    }
}