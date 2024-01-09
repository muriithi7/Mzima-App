package com.FarmProduce.FarmApp.ErrorHandling;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super  ("could not find the user you are looking for which is id "+id);
    }
}
