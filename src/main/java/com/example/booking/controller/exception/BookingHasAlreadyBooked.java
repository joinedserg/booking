package com.example.booking.controller.exception;

import com.example.booking.dto.Booking;

public class BookingHasAlreadyBooked extends RuntimeException {

    public BookingHasAlreadyBooked(Booking dto) {
        super("Booking has already booked: " + dto);
    }

}
