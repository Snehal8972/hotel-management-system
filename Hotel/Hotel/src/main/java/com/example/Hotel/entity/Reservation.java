

package com.example.Hotel.entity;
//
import com.example.Hotel.dto.ReservationDto;
import com.example.Hotel.enums.ReservationStatus;
import com.example.Hotel.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity @Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long price;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus = ReservationStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.NOT_AVAILABLE;

    private String transactionId;
    private LocalDate paymentDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ReservationDto getReservationDto() {
        ReservationDto dto = new ReservationDto();
        dto.setId(id);
        dto.setPrice(price);
        dto.setCheckInDate(checkInDate);
        dto.setCheckOutDate(checkOutDate);
        dto.setReservationStatus(reservationStatus);
        dto.setPaymentStatus(paymentStatus);
        dto.setTransactionId(transactionId);
        dto.setPaymentDate(paymentDate);
        dto.setRoomId(room.getId());
        dto.setRoomName(room.getName());
        dto.setRoomType(room.getType());
        dto.setUserId(user.getId());
        dto.setUsername(user.getName());
        return dto;
    }
}

