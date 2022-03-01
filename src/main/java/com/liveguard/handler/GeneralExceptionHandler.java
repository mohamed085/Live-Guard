package com.liveguard.handler;

import com.liveguard.exception.BusinessException;
import com.liveguard.payload.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler  {


    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e,
                                                                 WebRequest request) {
        log.error("GeneralExceptionHandler | handleBusinessException | exception: " + e.getMessage());
        ErrorResponse errorResponse= new ErrorResponse();

        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, e.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.error("GeneralExceptionHandler | handleMethodArgumentNotValid | exception: " + ex.getMessage());

        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            log.error("GeneralExceptionHandler | handleMethodArgumentNotValid | fieldName: " + fieldName + ", errorMessage: " + errorMessage);
            errors.add(errorMessage);
        });

        return ResponseEntity
                .badRequest()
                .body(errors);
    }




}
