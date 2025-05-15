package com.affles.watchout.server.domain.travel.repository;

import com.affles.watchout.server.domain.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRepository extends JpaRepository<Travel, Long> {
}