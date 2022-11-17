package com.example.booking.service;

import com.example.booking.controller.exception.BookingHasAlreadyBooked;
import com.example.booking.controller.exception.BookingNotFoundException;
import com.example.booking.db.entity.BookingEntity;
import com.example.booking.db.repository.BookingRepository;
import com.example.booking.dto.Booking;
import com.example.booking.mapper.BookingMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository repository;
    private final BookingMapper mapper;

    public BookingEntity getBookingById(Long booking) {
        return repository.findById(booking)
                .orElseThrow(() -> new BookingNotFoundException(booking));
    }

    public void createBooking(@Valid Booking dto) {
        try {
            repository.save(mapper.toEntity(dto));
        }
        catch (Exception ex) {
            throw new BookingHasAlreadyBooked(dto);
        }
    }

    public void cancelBooking(Long bookingId) {
        try {
            repository.deleteById(bookingId);
        }
        catch (Exception ex) {
            throw new BookingNotFoundException(bookingId);
        }
    }

    public List<Booking> getAllBookingByDate(LocalDate date) {
        return repository.getBookingByDate(date)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
