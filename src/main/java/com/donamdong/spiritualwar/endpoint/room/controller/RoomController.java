package com.donamdong.spiritualwar.endpoint.room.controller;

import com.donamdong.spiritualwar.domain.Room;
import com.donamdong.spiritualwar.endpoint.room.dto.request.RoomRequest;
import com.donamdong.spiritualwar.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/save")
    ResponseEntity<Room> saveRoom(@RequestBody RoomRequest roomRequest) {
        return ResponseEntity.ok(roomService.saveUpdateRoom(
                Room.builder()
                        .roomName(roomRequest.getRoomName())
                        .maxCapacity(roomRequest.getMaxCapacity())
                        .build()
        ));
    }

    @PostMapping("/delete/{roomIdx}")
    ResponseEntity<String> deleteRoom(@PathVariable String roomIdx) {
        roomService.deleteRoom(Long.parseLong(roomIdx));

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    ResponseEntity<List<Room>> getRooms() {
        return ResponseEntity.ok(roomService.getRooms());
    }

}
