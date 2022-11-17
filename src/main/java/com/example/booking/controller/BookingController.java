package com.example.booking.controller;

import com.example.booking.db.entity.BookingEntity;
import com.example.booking.dto.Booking;
import com.example.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("booking")
@AllArgsConstructor
public class BookingController {

    private final BookingService service;

    @GetMapping("/getBooking/{bookingId}")
    public BookingEntity getBooking(@PathVariable Long bookingId) {
        return service.getBookingById(bookingId);
    }

    @DeleteMapping("/cancelBooking/{bookingId}")
    public void cancelBooking(@PathVariable Long bookingId) {
        service.cancelBooking(bookingId);
    }

    @PostMapping("/createBooking/{filmId}/{dateOfBooking}/{seat}")
    public void createBooking(@Valid Booking booking
    ) {
        service.createBooking(booking);
    }

    @GetMapping("/listBookings/{date}")
    public List<Booking> listBookings(@Parameter @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                          LocalDate date) {
        return service.getAllBookingByDate(date);
    }

}
