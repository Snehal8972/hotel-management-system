





package com.example.Hotel.controller.admin;

import com.example.Hotel.dto.RoomDto;
import com.example.Hotel.dto.RoomResponseDto;
import com.example.Hotel.services.admin.rooms.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class RoomsController {

    private final RoomService roomService;

    @PostMapping("/room")
    public ResponseEntity<?> postRoom(@RequestBody RoomDto roomDto) {
        boolean success = roomService.postRoom(roomDto);
        return success ? ResponseEntity.ok().build() :
                ResponseEntity.badRequest().body("Room could not be saved");
    }

    @GetMapping("/rooms/{pageNumber}")
    public ResponseEntity<RoomResponseDto> getAllRooms(@PathVariable int pageNumber){
        return ResponseEntity.ok(roomService.getAllRooms(pageNumber));
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id){
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PutMapping("/room/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable Long id, @RequestBody RoomDto roomDto){
        return roomService.updateRoom(id, roomDto) ? ResponseEntity.ok().build() :
                ResponseEntity.badRequest().body("Room not found");
    }

    @DeleteMapping("/room/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id){
        roomService.deleteRoom(id);
        return ResponseEntity.ok().build();
    }
}
