package com.FarmProduce.FarmApp.auth;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ResponseModel {
    private String message;
    private String technicalMessage = "Success";
    private boolean status = true;
    private Integer responseStatus = HttpStatus.OK.value();
    //private String timestamp = HelperUtility.getDateString();
    private String exception;
    private Object data;


}
