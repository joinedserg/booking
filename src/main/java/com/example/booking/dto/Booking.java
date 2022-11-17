package com.example.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    private Long bookingId;

    @NotNull(message = "filmId is mandatory")
    @Min(0) @Max(4) //TODO add films table and make constraint
    private Long filmId;

    @NotNull(message = "dateOfBooking is mandatory")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBooking;

    @NotNull(message = "seat is mandatory")
    @Min(0) @Max(99) //TODO add cinema_halls and make it configurable
    private Integer seat;

}
