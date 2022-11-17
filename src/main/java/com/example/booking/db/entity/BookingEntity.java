package com.example.booking.db.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
@Data
@NoArgsConstructor
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "film_id")
    private Long filmId;

    @Column(name = "date_of_booking")
    private LocalDate dateOfBooking;

    private Integer seat;

}
