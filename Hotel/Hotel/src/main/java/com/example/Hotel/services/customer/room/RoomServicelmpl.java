package com.example.Hotel.services.customer.room;

import com.example.Hotel.dto.RoomResponseDto;
import com.example.Hotel.entity.Room;
import com.example.Hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServicelmpl  implements RoomService{
private final RoomRepository roomRepository;

@Override
    public RoomResponseDto getAvailavleRooms(int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Room> roomPage = roomRepository.findByAvailable(true,pageable);

        RoomResponseDto response = new RoomResponseDto();
        response.setPageNumber(roomPage.getNumber());
        response.setTotalPages(roomPage.getTotalPages());
        response.setRoomDtoList(
                roomPage.getContent()
                        .stream()
                        .map(Room::getRoomDto)
                        .collect(Collectors.toList())
        );

        return response;
    }
}
