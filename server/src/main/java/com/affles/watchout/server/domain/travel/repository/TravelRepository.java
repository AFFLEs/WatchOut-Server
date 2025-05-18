package com.affles.watchout.server.domain.travel.repository;

import com.affles.watchout.server.domain.travel.entity.Travel;
import com.affles.watchout.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TravelRepository extends JpaRepository<Travel, Long> {
    Optional<Travel> findFirstByUser(User user); // 하나만 가져오기 위해 설정
}