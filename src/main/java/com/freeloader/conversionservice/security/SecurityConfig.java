package com.freeloader.conversionservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SecurityConfig {

	/*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests()
                    .requestMatchers("/foods/uom", "/convert", "/foods/{foodId}/uom").permitAll()
                    .requestMatchers("/foods").authenticated()
                    .anyRequest().authenticated()
                .and()
                    .oauth2Login()
                .and()
                    .build();
    }
    */
}
