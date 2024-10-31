package com.example.springboot.repositories;

import com.example.springboot.models.Bases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BasesRepository extends JpaRepository<Bases, Integer> {
}
