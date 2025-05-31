package com.affles.watchout.server.domain.spot.entity;
import com.affles.watchout.server.domain.travel.entity.Travel;
import com.affles.watchout.server.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "spot")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Spot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spotId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;

    private LocalDate spotDate;

    @Column(length = 50)
    private String spotName;

    @Column(length = 200)
    private String spotDetail;

    private Double latitude;

    private Double longitude;

    private LocalTime spotTime;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String country;
}