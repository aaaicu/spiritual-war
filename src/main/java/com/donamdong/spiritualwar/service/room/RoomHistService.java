package com.donamdong.spiritualwar.service.room;

import com.donamdong.spiritualwar.domain.RoomHist;
import com.donamdong.spiritualwar.repository.room.RoomHistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomHistService {
    final private RoomHistRepository roomHistRepository;

    public List<RoomHist> getGameRoomHist(Long idx) {
        return roomHistRepository.findByGameIdx(idx);
    }

    public void deleteRoom(Long idx) {
        roomHistRepository.deleteById(idx);
    }

    public RoomHist saveUpdateRoom(RoomHist roomHist) {
        return roomHistRepository.save(roomHist);
    }
}
