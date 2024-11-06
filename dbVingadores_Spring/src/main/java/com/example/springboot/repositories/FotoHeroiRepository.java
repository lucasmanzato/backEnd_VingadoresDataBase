package com.example.springboot.repositories;

import com.example.springboot.models.FotoHeroi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoHeroiRepository extends JpaRepository<FotoHeroi, Long> {
    List<FotoHeroi> findByHeroiId(Long heroiId); // Busca fotos por ID do herói
}
