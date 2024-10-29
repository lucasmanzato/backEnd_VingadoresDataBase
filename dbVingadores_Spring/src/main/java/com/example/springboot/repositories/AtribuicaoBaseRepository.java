package com.example.springboot.repositories;

import com.example.springboot.models.AtribuicaoBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AtribuicaoBaseRepository extends JpaRepository<AtribuicaoBase, UUID> {
}
