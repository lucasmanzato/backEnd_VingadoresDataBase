package com.example.springboot.dtos;

import java.util.UUID;

public record HeroiRecordDto(UUID id, String nome, String poder, String afiliacao, String codinome, String status, String localizacao ) {
}
