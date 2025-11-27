package com.example.Hotel.services.admin.reservation;

import com.example.Hotel.dto.ReservationResponseDto;

public interface ReservationService {

    ReservationResponseDto getAllReservations(int pageNumber);
    boolean changeReservationsStatus(Long id,String status);
}









