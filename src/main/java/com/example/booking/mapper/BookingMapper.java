package com.example.booking.mapper;

import com.example.booking.db.entity.BookingEntity;
import com.example.booking.dto.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(target = "bookingId", source = "id")
    Booking toDto(BookingEntity entity);

    @Mapping(target = "id", source = "bookingId")
    BookingEntity toEntity(Booking dto);
}
