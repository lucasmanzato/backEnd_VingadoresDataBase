package com.example.springboot.repositories;

import com.example.springboot.models.Heroi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HeroiRepository extends JpaRepository<Heroi, UUID> {
}
