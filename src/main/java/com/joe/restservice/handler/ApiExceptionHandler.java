package com.joe.restservice.handler;

import com.joe.restservice.exception.InvalidRequestException;
import com.joe.restservice.exception.NotFoundException;
import com.joe.restservice.resource.ErrorResource;
import com.joe.restservice.resource.FieldResource;
import com.joe.restservice.resource.InvalidErrorResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    /*
     * 異常處理
     * 處理資源找不到。
     * */
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handlerNotFound(RuntimeException e){
        ErrorResource errorResource = new ErrorResource(e.getMessage());
        return new ResponseEntity<>(errorResource, HttpStatus.NOT_FOUND);
    }
    /*
     * 異常處理
     * 處理參數驗證失敗。
     * */
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseBody
    public ResponseEntity<?> handlerInvalidRequest(InvalidRequestException e){
        Errors errors=e.getErrors();
        List<FieldResource> fieldResources = new ArrayList<>();
        List<FieldError> fieldErrors = errors.getFieldErrors();
        for (FieldError fieldError:fieldErrors){
            FieldResource fieldResource = new FieldResource(fieldError.getObjectName(),
                    fieldError.getField(),
                    fieldError.getCode(),
                    fieldError.getDefaultMessage());

            fieldResources.add(fieldResource);
        }
        InvalidErrorResource ier = new InvalidErrorResource(e.getMessage(),fieldResources);
        return new ResponseEntity<Object>(ier, HttpStatus.BAD_REQUEST);
    }

    /*
     * 異常處理
     * 處理全局異常。
     * */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handlerException(Exception e){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
