package com.donamdong.spiritualwar.repository.game;

import com.donamdong.spiritualwar.domain.*;
import com.donamdong.spiritualwar.repository.user.UserRepository;
import com.donamdong.spiritualwar.util.DevilCodeGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class GameRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GameSettingRepository gameSettingRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameRoundRepository gameRoundRepository;

    @Autowired
    GameParticipationRepository gameParticipationRepository;

    @Autowired
    DevilCodeRepository devilCodeRepository;

    @Autowired
    DevilCodeGenerator devilCodeGenerator;



    @Test
    public void 저장() {

        User saveUser1 = userRepository.findById(1L).orElse(null);
        User saveUser2 = userRepository.findById(2L).orElse(null);

        GameSetting saveGameSetting = gameSettingRepository.save(GameSetting.builder()
                .totalRound(5)
                .afternoonMinute(8)
                .nightMinute(4)
                .holidayNightMinute(12)
                .citizen(12)
                .devil(3)
                .createDt(LocalDateTime.now())
                .build());

        Game saveGame = gameRepository.save(Game.builder()
                .gameSetting(saveGameSetting)
                .createDt(LocalDateTime.now())
            .build());

        GameRound saveGameRound = gameRoundRepository.save(GameRound.builder()
                .game(saveGame)
                .sortingOrder(1)
                .holidayYn(false)
                .successYn(false)
                .startDt(LocalDateTime.now())
                .endDt(LocalDateTime.now())
            .build());

        GameParticipation saveGameParticipation1 = gameParticipationRepository.save(GameParticipation.builder()
                .game(saveGame)
                .user(saveUser1)
                .bestFriend(null)
                .participationRole(Role.CITIZEN)
                .hiredDt(null)
                .createDt(LocalDateTime.now())
                .build());

        GameParticipation saveGameParticipation2 = gameParticipationRepository.save(GameParticipation.builder()
                .game(saveGame)
                .user(saveUser2)
                .bestFriend(null)
                .participationRole(Role.CITIZEN)
                .hiredDt(null)
                .createDt(LocalDateTime.now())
                .build());


        saveGameParticipation1.setBestFriend(saveGameParticipation2);

        saveGameParticipation2.setBestFriend(saveGameParticipation1);
        saveGameParticipation2.setHireDevil(saveGameParticipation1);
        saveGameParticipation2.setHiredDt(LocalDateTime.now());
        gameParticipationRepository.save(saveGameParticipation1);
        gameParticipationRepository.save(saveGameParticipation2);



        List<GameSetting> gameSettingList = gameSettingRepository.findAll();
        List<Game> gameList = gameRepository.findAll();
        List<GameRound> gameRoundList = gameRoundRepository.findAll();
        List<GameParticipation> gameParticipationList = gameParticipationRepository.findAll();


        GameSetting gameSetting = gameSettingList.get(0);
        Game game = gameList.get(0);
        GameRound gameRound = gameRoundList.get(0);
        GameParticipation gameParticipation = gameParticipationList.get(0);

        String code = devilCodeGenerator.excuteGenerate();

        devilCodeRepository.save(DevilCode.builder()
                .participation(saveGameParticipation1)
                .devilCode(code)
                .registerYn(false)
                .createDt(LocalDateTime.now())
                .expireDt(LocalDateTime.now().plusMinutes(30L))
                .build());

        System.out.println("gameSetting = " + gameSetting);
        System.out.println("game = " + game);
        System.out.println("gameRound = " + gameRound);
        System.out.println("gameParticipation = " + gameParticipation);


    }
}