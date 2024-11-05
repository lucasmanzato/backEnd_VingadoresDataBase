package com.example.springboot.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/**").permitAll()  // Permitir todas as requisições
                        .requestMatchers("/entrar", "/entrar/**").permitAll()
                        .requestMatchers("/herois", "/herois/**").permitAll()
                        .requestMatchers("/api/alocacao-recursos", "/api/alocacao-recursos/**").permitAll()
                        .requestMatchers("/atribuicaoBase", "/atribuicaoBase/**").permitAll()
                        .requestMatchers("/api/envolvimento-evento", "/api/envolvimento-evento/**").permitAll()
                        .requestMatchers("/api/envolvimento-vilao", "/api/envolvimento-vilao/**").permitAll()
                        .requestMatchers("/eventosHistoricos", "/eventosHistoricos/**").permitAll()
                        .requestMatchers("/missoes", "/missoes/**").permitAll()
                        .requestMatchers("/api/participacao-evento", "/api/participacao-evento/**").permitAll()
                        .requestMatchers("/api/participacao-herois", "/api/participacao-herois/**").permitAll()
                        .requestMatchers("/recursos", "/recursos/**").permitAll()
                        .requestMatchers("/api/utilizacao-recursos", "/api/utilizacao-recursos/**").permitAll()
                        .requestMatchers("/viloes", "/viloes/**").permitAll()
                        .anyRequest().permitAll()
                )
                .httpBasic().disable()  // Desabilita a autenticação básica
                .formLogin().disable();  // Desabilita o login por formulário

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost", "http://127.0.0.1:5500"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }
}
