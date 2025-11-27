package com.example.Hotel.services.customer.room;

import com.example.Hotel.dto.RoomResponseDto;

public interface RoomService {
    RoomResponseDto getAvailavleRooms(int pageNumber);
}
