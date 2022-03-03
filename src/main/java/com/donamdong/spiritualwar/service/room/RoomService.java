package com.donamdong.spiritualwar.service.room;

import com.donamdong.spiritualwar.domain.Room;
import com.donamdong.spiritualwar.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    final private RoomRepository roomRepository;

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public void deleteRoom(Long idx) {
        roomRepository.deleteById(idx);
    }

    public Room saveUpdateRoom(Room room) {
        return roomRepository.save(room);
    }
}
