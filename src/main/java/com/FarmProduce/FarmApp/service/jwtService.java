package com.FarmProduce.FarmApp.service;

import com.FarmProduce.FarmApp.model.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
@Service
public class jwtService {

    private static final String Secret_key = "1234";
    public String generateToken(UserModel usermodel, Collection<SimpleGrantedAuthority> authorities){
        Algorithm algorithm = Algorithm.HMAC256(Secret_key.getBytes());
        return JWT.create()
                .withSubject(usermodel.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+50*60*1000))
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }
    public String generateRefreshToken(UserModel usermodel, Collection<SimpleGrantedAuthority> authorities){
        Algorithm algorithm = Algorithm.HMAC256(Secret_key.getBytes());
        return JWT.create()
                .withSubject(usermodel.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+50*60*1000))
                .sign(algorithm);
    }
}
