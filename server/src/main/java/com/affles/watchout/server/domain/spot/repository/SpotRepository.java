package com.affles.watchout.server.domain.spot.repository;

import com.affles.watchout.server.domain.spot.entity.Spot;
import com.affles.watchout.server.domain.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SpotRepository extends JpaRepository<Spot, Long> {

    List<Spot> findAllByTravelAndSpotDateOrderBySpotTimeAsc(Travel travel, LocalDate date);

    List<Spot> findAllByTravel(Travel travel);

}