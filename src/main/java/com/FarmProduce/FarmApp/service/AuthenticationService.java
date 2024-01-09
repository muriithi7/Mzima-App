package com.FarmProduce.FarmApp.service;

import com.FarmProduce.FarmApp.ErrorHandling.GlobalExceptionHandler;
import com.FarmProduce.FarmApp.ErrorHandling.ResourceNotFoundException;
import com.FarmProduce.FarmApp.ErrorHandling.UnauthorizedException;
import com.FarmProduce.FarmApp.auth.AuthenticationRequest;
import com.FarmProduce.FarmApp.auth.AuthenticationResponse;
import com.FarmProduce.FarmApp.model.UserModel;
import com.FarmProduce.FarmApp.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;



import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final jwtService jwtservice;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest)  throws UnauthorizedException {

        
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        UserModel usermodel = userRepo.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new UnauthorizedException("user not found"));
        if (usermodel == null) {

            throw new ResourceNotFoundException("user not found");


        }
        if (usermodel != null) {


            String password = authenticationRequest.getPassword();
            String encodedPassword = usermodel.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);

            if (usermodel.getUsername().matches(authenticationRequest.getUsername())) {

                if (isPwdRight) {

                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    usermodel.getRoles().forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role.getName()));
                    });

                    var jwtToken = jwtservice.generateToken(usermodel, authorities);
                    var jwtRefreshToken = jwtservice.generateRefreshToken(usermodel, authorities);
                    var username = usermodel.getUsername();
                    var name = usermodel.getName();
                    var id = usermodel.getId();

                    var status = "login";

                    return AuthenticationResponse.builder().token(jwtToken).refreshToken(jwtRefreshToken).username(username).id(id).name(name).status(status).build();

                }


            }



        }

         Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
         usermodel.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        var jwtToken = jwtservice.generateToken(usermodel, authorities);
        var jwtRefreshToken = jwtservice.generateRefreshToken(usermodel, authorities);
        return AuthenticationResponse.builder().token(jwtToken).refreshToken(jwtRefreshToken).build();



    }
}