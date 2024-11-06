package com.example.springboot.services;

import com.example.springboot.models.FotoHeroi;
import com.example.springboot.models.Heroi;
import com.example.springboot.repositories.FotoHeroiRepository;
import com.example.springboot.repositories.HeroiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FotoHeroiService {

    @Autowired
    private FotoHeroiRepository fotoHeroiRepository;

    @Autowired
    private HeroiRepository heroiRepository;

    public List<FotoHeroi> listarFotosPorHeroi(Long heroiId) {
        return fotoHeroiRepository.findByHeroiId(heroiId);
    }

    public FotoHeroi salvarFoto(FotoHeroi fotoHeroi) {
        return fotoHeroiRepository.save(fotoHeroi);
    }

    public void deletarFoto(Long id) {
        fotoHeroiRepository.deleteById(id);
    }

    public Heroi getHeroiById(Long heroiId) {
        return heroiRepository.findById(heroiId.intValue()).orElse(null);
    }

}
