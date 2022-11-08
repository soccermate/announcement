package com.example.announcement.controller.exceptionHandler;

import com.example.announcement.controller.exceptionHandler.dto.ErrorMsgDto;
import com.example.announcement.exceptions.ResourceNotFoundException;
import com.example.announcement.exceptions.TitleDuplicateException;
import com.example.announcement.exceptions.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Date;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(UnAuthorizedException.class)
    ResponseEntity UnAuthorizedException(UnAuthorizedException ex) {
        log.debug("handling exception::" + ex);

        return new ResponseEntity(new ErrorMsgDto(new Date(), "unauthorized to perform action", ex.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TitleDuplicateException.class)
    ResponseEntity TitleDuplicateException(TitleDuplicateException ex) {
        log.debug("handling exception::" + ex);

        return new ResponseEntity(new ErrorMsgDto(new Date(), "cannot create announcement with same title",
                ex.getMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity ResourceNotFoundException(ResourceNotFoundException ex) {
        log.debug("handling exception::" + ex);

        return new ResponseEntity(new ErrorMsgDto(new Date(), "the requested resource is not found!",
                ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({WebExchangeBindException.class})
    ResponseEntity webExchangeBindException(WebExchangeBindException ex)
    {
        String rejectedValue = ex.getBindingResult().getFieldError().getRejectedValue() == null? ""
                : ex.getBindingResult().getFieldError().getRejectedValue().toString();
        String message = ex.getBindingResult().getFieldError().getDefaultMessage() == null? ""
                :ex.getBindingResult().getFieldError().getDefaultMessage();

        return new ResponseEntity(
                new ErrorMsgDto(new Date(), "bad request ", rejectedValue
                        + " is not valid with message : "
                        + message),
                HttpStatus.BAD_REQUEST
        );
    }
}
