package com.example.booking.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookingControllerAdvice {

    @ResponseBody
    @ExceptionHandler(BookingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bookingNotFound(BookingNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(BookingHasAlreadyBooked.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bookingHalAlreadyBooked(BookingHasAlreadyBooked ex) {
        return ex.getMessage();
    }
}
