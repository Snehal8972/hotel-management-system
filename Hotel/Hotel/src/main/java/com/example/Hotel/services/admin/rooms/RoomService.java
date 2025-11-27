

package com.example.Hotel.services.admin.rooms;

import com.example.Hotel.dto.RoomDto;
import com.example.Hotel.dto.RoomResponseDto;

public interface RoomService {
    boolean postRoom(RoomDto roomDto);
    RoomResponseDto getAllRooms(int pageNumber);
    RoomDto getRoomById(Long id);
    boolean updateRoom(Long id, RoomDto roomDto);
    void deleteRoom(Long id);
}
