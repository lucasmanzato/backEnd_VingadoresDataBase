package com.example.springboot.repositories;

import com.example.springboot.models.AlocacaoRecursos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface AlocacaoRecursosRepository extends JpaRepository<AlocacaoRecursos, Integer> {
}

