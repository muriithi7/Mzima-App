package com.FarmProduce.FarmApp.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private  Long id;
    private String username;
    private String name;
    private String token;
    private String refreshToken;
    private String status;
    //private String message;




}



