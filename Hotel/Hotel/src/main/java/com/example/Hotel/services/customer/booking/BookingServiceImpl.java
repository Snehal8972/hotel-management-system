

package com.example.Hotel.services.customer.booking;

import com.example.Hotel.dto.ReservationDto;
import com.example.Hotel.dto.ReservationResponseDto;
import com.example.Hotel.entity.Reservation;
import com.example.Hotel.entity.Room;
import com.example.Hotel.entity.User;
import com.example.Hotel.enums.ReservationStatus;
import com.example.Hotel.enums.PaymentStatus;
import com.example.Hotel.repository.ReservationRepository;
import com.example.Hotel.repository.RoomRepository;
import com.example.Hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public static final int SEARCH_RESULT_PER_PAGE = 4;

    @Override
    public boolean postReservation(ReservationDto reservationDto) {
        Room room = roomRepository.findById(reservationDto.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
        User user = userRepository.findById(reservationDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setUser(user);
        reservation.setCheckInDate(reservationDto.getCheckInDate());
        reservation.setCheckOutDate(reservationDto.getCheckOutDate());
        reservation.setReservationStatus(ReservationStatus.PENDING);
        reservation.setPaymentStatus(PaymentStatus.NOT_AVAILABLE);

        long days = ChronoUnit.DAYS.between(reservationDto.getCheckInDate(), reservationDto.getCheckOutDate());
        if (days == 0) days = 1; // Minimum 1 day
        reservation.setPrice(room.getPrice() * days);

        reservationRepository.save(reservation);
        return true;
    }

    @Override
    public ReservationResponseDto getAllReservationByUserId(Long userId, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
        Page<Reservation> reservationPage = reservationRepository.findAllByUserId(pageable, userId);

        ReservationResponseDto responseDto = new ReservationResponseDto();
        responseDto.setReservationDtoList(reservationPage.stream()
                .map(Reservation::getReservationDto)
                .collect(Collectors.toList()));
        responseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
        responseDto.setTotalPages(reservationPage.getTotalPages());
        return responseDto;
    }

//    @Override
//    public ReservationDto payReservation(Long reservationId) {
//        Reservation reservation = reservationRepository.findById(reservationId)
//                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
//
//        if (reservation.getReservationStatus() != ReservationStatus.APPROVED) {
//            throw new IllegalStateException("Cannot pay before approval");
//        }
//
//        reservation.setPaymentStatus(PaymentStatus.PAID);
//        reservation.setTransactionId("TXN" + System.currentTimeMillis());
//        reservation.setPaymentDate(LocalDate.now());
//        reservationRepository.save(reservation);
//
//        return reservation.getReservationDto();
//    }

    @Override
    public ReservationDto payReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if(reservation.getReservationStatus() != ReservationStatus.APPROVED)
            throw new IllegalStateException("Cannot pay before approval");

        reservation.setPaymentStatus(PaymentStatus.PAID);
        reservation.setTransactionId("TXN" + System.currentTimeMillis());
        reservation.setPaymentDate(LocalDate.now());
        reservationRepository.save(reservation);

        return reservation.getReservationDto();
    }


}












