package com.example.springboot.models;

import com.example.springboot.models.Heroi;
import jakarta.persistence.*;

@Entity
public class FotoHeroi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "heroi_id")
    private Heroi heroi; // Relacionamento com a classe Heroi

    @Lob
    private byte[] imagem;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Heroi getHeroi() {
        return heroi;
    }

    public void setHeroi(Heroi heroi) {
        this.heroi = heroi;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    // Método para retornar a imagem em Base64
    public String getImagemBase64() {
        return java.util.Base64.getEncoder().encodeToString(this.imagem);
    }
}
