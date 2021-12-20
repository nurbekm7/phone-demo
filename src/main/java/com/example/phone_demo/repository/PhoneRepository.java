package com.example.phone_demo.repository;

import com.example.phone_demo.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    Optional<Phone> findByVerificationHash(String hash);

    List<Phone> findByName(String name);
}
