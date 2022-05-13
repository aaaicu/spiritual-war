package com.donamdong.spiritualwar.service.game;

import com.donamdong.spiritualwar.common.exception.ApiException;
import com.donamdong.spiritualwar.common.exception.ErrorCode;
import com.donamdong.spiritualwar.domain.*;
import com.donamdong.spiritualwar.domain.dto.MemberDTO;
import com.donamdong.spiritualwar.repository.game.GameParticipationRepository;
import com.donamdong.spiritualwar.repository.game.GameRepository;
import com.donamdong.spiritualwar.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GameParticipationService {
    private final GameParticipationRepository gameParticipationRepository;
    private final GameRepository gameRepository;
    private final UserService userService;
    private final GameService gameService;

    public void joinGame(Long gameIdx, Long userIdx) {
        Game game = gameService.getGame(gameIdx);
        User user = userService.getUser(userIdx);
        gameParticipationRepository.save(GameParticipation.builder()
                .game(game)
                .user(user)
                .bestFriend(null)
                .participationRole(Role.CITIZEN)
                .hiredDt(null)
                .createDt(LocalDateTime.now())
                .build());

    }

    public List<GameParticipation> findGameMember(Long userIdx) {
        User user = userService.getUser(userIdx);

        List<Game> games = gameRepository.findGameParticipationByUserIdx(user);
        if (games.isEmpty()) {
            return List.of();
        } else {
            return gameParticipationRepository.findAllUserByGameJoin(games.get(0));
        }
    }

    public List<GameParticipation> findGameMemberAll(Long gameIdx) {
        Game game = gameRepository.getById(gameIdx);
        List<GameParticipation> gameMember = gameParticipationRepository.findAllUserByGameJoin(game);
        if (gameMember.size() == 0) {
            return List.of();
        } else {
            return gameMember;
        }
    }

    public List<GameParticipation> startMemberSetting(Long gameIdx) {
        List<GameParticipation> gameMemberAll = findGameMemberAll(gameIdx);

        if (gameMemberAll.size() < 14 || gameMemberAll.size() > 30) {
            throw new ApiException(ErrorCode.MEMBER_COUNT_NOT_ENOUGH);
        }

        gameMemberAll.forEach(participation -> {
            participation.setBestFriend(null);
            participation.setHolyWatching(null);
            participation.setFan(null);
            participation.setParticipationRole(Role.CITIZEN);
        });

        int devilCount = 0;
        int bestFriendCount = 2;
        int fanCount = 0;
        int holySpirit = 0;

        switch (gameMemberAll.size()) {
            case 14,15, 16, 17, 18, 19 -> {
                devilCount = 3;
                fanCount = 1;
                holySpirit = 1;
            }
            case 20, 21, 22 -> {
                devilCount = 4;
                fanCount = 1;
                holySpirit = 1;
            }
            case 23, 24, 25 -> {
                devilCount = 4;
                fanCount = 2;
                holySpirit = 1;
            }
            case 26, 27 -> {
                devilCount = 4;
                fanCount = 2;
                holySpirit = 2;
            }
            case 28, 29, 30 -> {
                devilCount = 4;
                fanCount = 3;
                holySpirit = 2;
            }
        }

        Collections.shuffle(gameMemberAll);

        fanCount = fanCount * 2;

        //친구 (2명고정)
        gameMemberAll.get(0).setParticipationRole(Role.FRIEND);
        gameMemberAll.get(0).setBestFriend(gameMemberAll.get(1));

        gameMemberAll.get(1).setParticipationRole(Role.FRIEND);
        gameMemberAll.get(1).setBestFriend(gameMemberAll.get(0));


        int randomIdx = 2;

        // 악마 선출
        while (devilCount > 0) {
            gameMemberAll.get(randomIdx).setParticipationRole(Role.DEVIL);

            devilCount--;
            randomIdx++;
        }

        // 성령충만한자
        while (holySpirit > 0) {
            gameMemberAll.get(randomIdx).setParticipationRole(Role.HOLY);

            holySpirit--;
            randomIdx++;
        }

        // 팬
        while (fanCount > 0) {
            int before = randomIdx;
            gameMemberAll.get(before).setParticipationRole(Role.FAN);

            fanCount--;
            randomIdx++;

            gameMemberAll.get(before).setFan(gameMemberAll.get(randomIdx));

            fanCount--;
            randomIdx++;
        }


        return gameParticipationRepository.saveAll(gameMemberAll);
    }



}
