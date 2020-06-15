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

//將此Handler置於所有@Controller前啟動
@RestControllerAdvice
public class ApiExceptionHandler {

    /*
     * 異常處理
     * 處理資源找不到。
     * @ResponseBody返回JSON格式
     * */
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleNotFound(RuntimeException e){
        ErrorResource errorResource = new ErrorResource(e.getMessage());
//        返回errorResource跟404
        return new ResponseEntity<>(errorResource, HttpStatus.NOT_FOUND);
    }
    /*
     * 異常處理
     * 處理參數驗證失敗。
     * @ResponseBody返回JSON格式
     * */
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseBody
    public ResponseEntity<?> handleInvalidRequest(InvalidRequestException e){

//        獲取錯誤字段集合
        Errors errors=e.getErrors();

        List<FieldResource> fieldResources = new ArrayList<>();

        List<FieldError> fieldErrors = errors.getFieldErrors();

        for (FieldError fieldError:fieldErrors){
//            將fieldError內的資訊塞入自製的fieldResource中
            FieldResource fieldResource = new FieldResource(fieldError.getObjectName(),
                    fieldError.getField(),
                    fieldError.getCode(),
                    fieldError.getDefaultMessage());

            fieldResources.add(fieldResource);
        }
//      將fieldResource跟錯誤訊息塞入InvalidErrorResource中傳出
        InvalidErrorResource ier = new InvalidErrorResource(e.getMessage(),fieldResources);
        return new ResponseEntity<Object>(ier, HttpStatus.BAD_REQUEST);
    }

    /*
     * 異常處理
     * 處理全局異常。
     * @ResponseBody返回JSON格式
     * */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleException(Exception e){
//        其餘錯誤都塞入回傳至INTERNAL_SERVER_ERROR
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
