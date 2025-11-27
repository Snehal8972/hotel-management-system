
package com.example.Hotel.services.admin.rooms;

import com.example.Hotel.dto.RoomDto;
import com.example.Hotel.dto.RoomResponseDto;
import com.example.Hotel.entity.Room;
import com.example.Hotel.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public boolean postRoom(RoomDto roomDto) {
        try {
            Room room = new Room();
            room.setName(roomDto.getName());
            room.setPrice(roomDto.getPrice());
            room.setType(roomDto.getType());
            room.setAvailable(true);
            roomRepository.save(room);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public RoomResponseDto getAllRooms(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 2); // 2 rooms per page
        Page<Room> roomPage = roomRepository.findAll(pageable);

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

    @Override
    public RoomDto getRoomById(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if(optionalRoom.isPresent()){
            return optionalRoom.get().getRoomDto();
        } else {
            throw new EntityNotFoundException("Room not present");
        }
    }

    @Override
    public boolean updateRoom(Long id, RoomDto roomDto) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if(optionalRoom.isPresent()){
            Room existingRoom = optionalRoom.get();
            existingRoom.setName(roomDto.getName());
            existingRoom.setPrice(roomDto.getPrice());
            existingRoom.setType(roomDto.getType());
            roomRepository.save(existingRoom);
            return true;
        }
        return false;
    }

    @Override
    public void deleteRoom(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if(optionalRoom.isPresent()){
            roomRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Room not present");
        }
    }
}
