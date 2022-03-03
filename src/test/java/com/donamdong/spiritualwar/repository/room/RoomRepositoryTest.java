package com.donamdong.spiritualwar.repository.room;

import com.donamdong.spiritualwar.domain.*;
import com.donamdong.spiritualwar.repository.game.GameParticipationRepository;
import com.donamdong.spiritualwar.repository.game.GameRepository;
import com.donamdong.spiritualwar.repository.game.GameRoundRepository;
import com.donamdong.spiritualwar.repository.game.GameSettingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
class RoomRepositoryTest {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameSettingRepository gameSettingRepository;

    @Autowired
    RoomGameSettingRepository roomGameSettingRepository;

    @Autowired
    RoomHistRepository roomHistRepository;

    @Autowired
    GameRoundRepository gameRoundRepository;

    @Autowired
    GameParticipationRepository gameParticipationRepository;


    @Test
    public void 방저장() {

        List<Room> roomList = roomRepository.findAll();

        Room room = roomList.get(0);

        System.out.println("room = " + room.getRoomName());

        GameSetting gameSetting = gameSettingRepository.findAll().get(0);


        roomGameSettingRepository.save(RoomGameSetting.builder()
                .gameSetting(gameSetting)
                .room(room)
                .capacity(4)
                .build());


        GameRound gameRound = gameRoundRepository.findAll().get(0);

        GameParticipation gameParticipation = gameParticipationRepository.findAll().get(0);


        roomHistRepository.save(RoomHist.builder()
                .gameRound(gameRound)
                .participation(gameParticipation)
                .room(room)
                .build());




    }

}