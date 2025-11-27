package com.example.Hotel.services.admin.reservation;

import com.example.Hotel.dto.ReservationDto;
import com.example.Hotel.dto.ReservationResponseDto;
import com.example.Hotel.entity.Reservation;
import com.example.Hotel.entity.Room;
import com.example.Hotel.enums.PaymentStatus;
import com.example.Hotel.enums.ReservationStatus;
import com.example.Hotel.repository.ReservationRepository;
import com.example.Hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServicelmpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final RoomRepository roomRepository;

    public static final int SEARCH_PER_PAGE = 4;

    public ReservationResponseDto getAllReservations(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_PER_PAGE);
        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);
        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();
        reservationResponseDto.setReservationDtoList(reservationPage.stream().map(Reservation::getReservationDto)
                .collect(Collectors.toList()));

        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponseDto.setTotalPages(reservationPage.getTotalPages());
        return reservationResponseDto;
    }

    public boolean changeReservationsStatus(Long id, String status) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if ("APPROVED".equalsIgnoreCase(status)) {
            reservation.setReservationStatus(ReservationStatus.APPROVED);
            reservation.setPaymentStatus(PaymentStatus.WAIT);
            reservation.getRoom().setAvailable(false);
            roomRepository.save(reservation.getRoom());
        } else if ("REJECTED".equalsIgnoreCase(status)) {
            reservation.setReservationStatus(ReservationStatus.REJECTED);
            reservation.setPaymentStatus(PaymentStatus.NOT_AVAILABLE);
        } else {
            reservation.setReservationStatus(ReservationStatus.PENDING);
            reservation.setPaymentStatus(PaymentStatus.WAIT);
        }
        reservationRepository.save(reservation);
        return true;
    }
}















