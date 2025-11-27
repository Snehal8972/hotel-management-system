









package com.example.Hotel.services.customer.booking;

import com.example.Hotel.dto.ReservationDto;
import com.example.Hotel.dto.ReservationResponseDto;

public interface BookingService {
    boolean postReservation(ReservationDto reservationDto);
    ReservationResponseDto getAllReservationByUserId(Long userId, int pageNumber);

    // Payment method
    ReservationDto payReservation(Long reservationId);
}






