
package com.example.Hotel.dto;

import com.example.Hotel.enums.ReservationStatus;
import com.example.Hotel.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDto {

    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long price;

    private ReservationStatus reservationStatus;

    private Long roomId;
    private Long userId;

    private String roomName;
    private String roomType;
    private String username;

    // Payment-related fields
    private PaymentStatus paymentStatus;
    private String transactionId;
    private LocalDate paymentDate;
}



