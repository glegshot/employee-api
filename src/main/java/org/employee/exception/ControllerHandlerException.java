package org.employee.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.employee.exception.dto.ErrorResult;
import org.employee.exception.dto.FieldValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerHandlerException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        ErrorResult errorResult = new ErrorResult();
        for(FieldError fieldError: e.getBindingResult().getFieldErrors()){
            errorResult.getFieldErrors().add(new FieldValidationError(fieldError.getField(),fieldError.getDefaultMessage()));
        }

        return errorResult;
    }







}
