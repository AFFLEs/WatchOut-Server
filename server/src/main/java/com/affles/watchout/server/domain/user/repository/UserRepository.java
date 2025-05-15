package com.affles.watchout.server.domain.user.repository;

import com.affles.watchout.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}