package com.example.springboot.dtos;

import java.util.UUID;

public record HeroiRecordDto(int id, String nome, String poder, String afiliacao, String codinome, String status, String localizacao ) {
}
