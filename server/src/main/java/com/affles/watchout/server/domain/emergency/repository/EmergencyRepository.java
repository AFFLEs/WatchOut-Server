package com.affles.watchout.server.domain.emergency.repository;

import com.affles.watchout.server.domain.emergency.entity.Emergency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyRepository extends JpaRepository<Emergency, Long> {
}