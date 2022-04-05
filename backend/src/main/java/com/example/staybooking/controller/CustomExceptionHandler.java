package com.example.staybooking.controller;


import com.example.staybooking.exception.GCSUploadException;
import com.example.staybooking.exception.GeoCodingException;
import com.example.staybooking.exception.InvalidStayAddressException;
import com.example.staybooking.exception.StayNotExistException;
import com.example.staybooking.exception.UserAlreadyExistException;
import com.example.staybooking.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {


  @ExceptionHandler(UserAlreadyExistException.class)
  public final ResponseEntity<String> handleUserAlreadyExistExceptions(Exception ex,
      WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
  }


  @ExceptionHandler(StayNotExistException.class)
  public final ResponseEntity<String> handleStayNotExistExceptions(Exception ex,
      WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserNotExistException.class)
  public final ResponseEntity<String> handleUserNotExistExceptions(Exception ex,
      WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);

  }

  @ExceptionHandler(GCSUploadException.class)
  public final ResponseEntity<String> handleGCSUploadExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(GeoCodingException.class)
  public final ResponseEntity<String> handleGeoCodingExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(InvalidStayAddressException.class)
  public final ResponseEntity<String> handleInvalidStayAddressExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }




}