package com.affles.watchout.server.domain.spot.repository;

import com.affles.watchout.server.domain.spot.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotRepository extends JpaRepository<Spot, Long> {
}