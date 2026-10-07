package com.example.seminar;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByEmailAndSeminar(String email, String seminar);
}
