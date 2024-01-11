package com.FarmProduce.FarmApp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static org.springframework.http.HttpMethod.GET;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor


public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                // .requestMatchers("/**","favicon.ico").permitAll()
                .requestMatchers(HttpMethod.POST, "/userlogin").permitAll()
                //.antMatchers("/api/upload-profile-picture").permitAll() // Allow uploading profile pictures
                // .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(GET, "/users").hasAnyAuthority("Role_Super_Admin")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

//        For H2 Console Support only
        http.headers().frameOptions().disable();

        //Allow form login
        http.formLogin().permitAll();

        return http.build();


    }


}
