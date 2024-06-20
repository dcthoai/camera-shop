package com.shop.repository;

import com.shop.model.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<CustomUserDetails, Integer> {
    Optional<CustomUserDetails> findByUsername(String username);
    Boolean existsByUsername(String username);
}