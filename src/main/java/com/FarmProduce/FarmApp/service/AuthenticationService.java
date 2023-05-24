package com.FarmProduce.FarmApp.service;

import com.FarmProduce.FarmApp.auth.AuthenticationRequest;
import com.FarmProduce.FarmApp.auth.AuthenticationResponse;
import com.FarmProduce.FarmApp.model.UserModel;
import com.FarmProduce.FarmApp.model.rolesModel;
import com.FarmProduce.FarmApp.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final jwtService jwtservice;



    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
        UserModel usermodel =userRepo.findByUsername(authenticationRequest.getUsername()).orElseThrow();
        if (usermodel !=null){


        }
        Collection<SimpleGrantedAuthority> authorities =new ArrayList<>();
        usermodel.getRoles().forEach(role ->{
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

       var jwtToken = jwtservice.generateToken(usermodel, authorities);
        var jwtRefreshToken = jwtservice.generateRefreshToken(usermodel, authorities);

        return AuthenticationResponse.builder().token(jwtToken).refreshToken(jwtRefreshToken).build();


    }
}
