package com.FarmProduce.FarmApp.ErrorHandling;

import com.FarmProduce.FarmApp.auth.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseModel> handleCustomException(CustomException ex) {
        ResponseModel errorResponse = new ResponseModel();
        errorResponse.setStatus(false);
        errorResponse.setResponseStatus(900);
        errorResponse.setMessage("Something went wrong");
        errorResponse.setTechnicalMessage("Technical error");
        errorResponse.setException(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseModel> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ResponseModel errorResponse = new ResponseModel();
        errorResponse.setStatus(false);
        errorResponse.setTechnicalMessage("Not Found");
        errorResponse.setResponseStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseModel> handleBadRequestException(BadRequestException ex) {
        ResponseModel errorResponse = new ResponseModel();
        errorResponse.setStatus(false);
        errorResponse.setResponseStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTechnicalMessage("Bad Request");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseModel> handleUnauthorizedException(UnauthorizedException ex) {
        ResponseModel errorResponse = new ResponseModel();
        errorResponse.setStatus(false);
        errorResponse.setResponseStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTechnicalMessage("Unauthorised");

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}
