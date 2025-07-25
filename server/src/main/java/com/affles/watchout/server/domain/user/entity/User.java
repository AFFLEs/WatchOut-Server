package com.affles.watchout.server.domain.user.entity;

import com.affles.watchout.server.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "`user`")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 20)
    private String name;

    private LocalDate birthdate;

    @Column(length = 50)
    private String email;

    @Column(length = 100)
    private String password;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 20)
    private String guardianPhone;

    // private String connectedWatch; 워치 연결

    // 동의 여부 필드들
    @Column(nullable = true, columnDefinition = "TINYINT(1)")
    private Boolean vibrationAlert;

    @Column(nullable = true, columnDefinition = "TINYINT(1)")
    private Boolean agreeEmergencyDataShare;

    @Column(nullable = true, columnDefinition = "TINYINT(1)")
    private Boolean allowLocationTracking;

    @Column(nullable = true, columnDefinition = "TINYINT(1)")
    private Boolean enableWatchEmergencySignal;
}
