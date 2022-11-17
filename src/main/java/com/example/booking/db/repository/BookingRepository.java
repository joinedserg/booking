package com.example.booking.db.repository;

import com.example.booking.db.entity.BookingEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends CrudRepository<BookingEntity, Long> {

    @Query("SELECT DISTINCT b FROM BookingEntity b WHERE b.dateOfBooking = :date")
    List<BookingEntity> getBookingByDate(@Param("date") LocalDate date);

}
