package com.example.springboot.models;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername("user")
                .password("123")
                .roles("USER")
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password("adm123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors() // Habilita o CORS
                .and()
                .csrf().disable() // Desativa o CSRF, necessário se não estiver usando tokens
                .authorizeHttpRequests(authorize -> authorize
                        // Permitir requisições anônimas para os endpoints que o frontend precisa
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/entrar", "/entrar/**").permitAll()
                        .requestMatchers("/herois", "/herois/**").permitAll()
                        .requestMatchers("/api/alocacao-recursos", "/api/alocacao-recursos/**").permitAll()
                        .requestMatchers("/atribuicaoBase", "/atribuicaoBase/**").permitAll()
                        .requestMatchers("/api/envolvimento-evento", "/api/envolvimento-evento/**").permitAll()
                        .requestMatchers("/api/envolvimento-vilao", "/api/envolvimento-vilao/**").permitAll()
                        .requestMatchers("/eventosHistoricos", "/eventosHistoricos/**").permitAll()
                        .requestMatchers("/missoes", "/missoes/**").permitAll() // Certifique-se de que "/missoes/**" está permitido
                        .requestMatchers("/api/participacao-evento", "/api/participacao-evento/**").permitAll()
                        .requestMatchers("/api/participacao-herois", "/api/participacao-herois/**").permitAll()
                        .requestMatchers("/recursos", "/recursos/**").permitAll()
                        .requestMatchers("/api/utilizacao-recursos", "/api/utilizacao-recursos/**").permitAll()
                        .requestMatchers("/viloes", "/viloes/**").permitAll()

                        // Restringe o acesso a URLs administrativas apenas para usuários com o papel ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Permite acesso a URLs de usuário apenas para usuários com o papel USER
                        .requestMatchers("/user/**").hasRole("USER")

                        // Exigir autenticação para qualquer outra URL
                        .anyRequest().authenticated()
                );

        return http.build();
    }


    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost", "http://127.0.0.1:5500")); // Inclui as origens do front-end
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }
}
