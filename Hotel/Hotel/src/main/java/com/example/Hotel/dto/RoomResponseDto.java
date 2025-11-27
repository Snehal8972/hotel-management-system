


package com.example.Hotel.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoomResponseDto {
    private Integer totalPages;
    private Integer pageNumber;
    private List<RoomDto> roomDtoList;
}
