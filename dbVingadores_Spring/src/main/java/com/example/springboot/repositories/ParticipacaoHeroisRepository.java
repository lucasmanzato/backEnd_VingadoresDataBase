package com.example.springboot.repositories;

import com.example.springboot.models.ParticipacaoHerois;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ParticipacaoHeroisRepository extends JpaRepository<ParticipacaoHerois, Integer> {
}
