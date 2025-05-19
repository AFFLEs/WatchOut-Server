package com.affles.watchout.server.domain.spot.repository;

import com.affles.watchout.server.domain.spot.entity.Spot;
import com.affles.watchout.server.domain.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SpotRepository extends JpaRepository<Spot, Long> {

    List<Spot> findTop3ByTravelAndSpotDateOrderBySpotTimeDesc(Travel travel, LocalDate date); // 내림차순 정렬

    List<Spot> findAllByTravelAndSpotDateOrderBySpotTimeAsc(Travel travel, LocalDate date); // 오름차순 정렬
}