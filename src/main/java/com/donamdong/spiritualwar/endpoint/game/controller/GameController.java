package com.donamdong.spiritualwar.endpoint.game.controller;

import com.donamdong.spiritualwar.domain.Game;
import com.donamdong.spiritualwar.domain.GameSetting;
import com.donamdong.spiritualwar.endpoint.game.dto.request.OpenGameRequest;
import com.donamdong.spiritualwar.endpoint.game.dto.request.SaveGameRequest;
import com.donamdong.spiritualwar.service.game.GameParticipationService;
import com.donamdong.spiritualwar.service.game.GameService;
import com.donamdong.spiritualwar.service.game.GameSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameSettingService gameSettingService;
    private final GameService gameService;
    private final GameParticipationService gameParticipationService;

    @PostMapping(value = "/open")
    public Game openGame(@RequestBody OpenGameRequest openGameRequest) {
        System.out.println("openGameRequest = " + openGameRequest.getGameSetting());
        return gameService.openNewGame(openGameRequest.getGameSetting());
    }

    @GetMapping("/fetch")
    ResponseEntity<List<Game>> fetchGame() {
        return ResponseEntity.ok(gameService.fetchCanJoinGameList().stream().filter(game -> game.getEndDt()!=null).collect(Collectors.toList()));
    }

    @PostMapping("/setting/save")
    ResponseEntity<GameSetting> saveSetting(@RequestBody SaveGameRequest saveGameRequest) {

        GameSetting setting = GameSetting.builder()
                .totalRound(saveGameRequest.getTotalRound())
                .afternoonMinute(saveGameRequest.getAfternoonMinute())
                .nightMinute(saveGameRequest.getNightMinute())
                .holidayNightMinute(saveGameRequest.getHolidayNightMinute())
                .citizen(saveGameRequest.getCitizen())
                .devil(saveGameRequest.getDevil())
                .createDt(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(gameSettingService.saveUpdateGameSetting(setting));
    }

    @PostMapping("/setting/delete/{gameSettingIdx}")
    ResponseEntity<String> deleteGameSetting(@PathVariable String gameSettingIdx) {
        gameSettingService.deleteGameSetting(Long.parseLong(gameSettingIdx));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/setting/{gameSettingIdx}")
    ResponseEntity<GameSetting> getGameSetting(@Valid @PathVariable String gameSettingIdx) {
        return ResponseEntity.ok(gameSettingService.getGameSetting(Long.parseLong(gameSettingIdx)));
    }

    @GetMapping("/join")
    ResponseEntity<String> joinGame(@RequestParam String gameIdx, @RequestParam String userIdx) {
        try {
            gameParticipationService.joinGame(Long.parseLong(gameIdx), Long.parseLong(userIdx));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("게임 참여 오류");
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    ResponseEntity<String> status(@RequestParam String gameIdx) {

        String result = "WAIT";
        Game game = gameService.getGame(Long.valueOf(gameIdx));

        //시작함
        if (game.getStartDt() == null) {
            result = "WAIT";
        } else if (game.getStartDt() != null && game.getEndDt() == null) {
            result = "START";
        } else if (game.getStartDt() != null && game.getEndDt() != null) {
            result = "END";
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/change/status")
    ResponseEntity<String> changeStatus(@RequestParam String gameIdx, @RequestParam String req) {
        Game game = gameService.getGame(Long.valueOf(gameIdx));
        if (req.equals("STOP")) {
            game.setEndDt(LocalDateTime.now());
        } else if (req.equals("START")) {
            gameParticipationService.startMemberSetting(game.getIdx());

            game.setStartDt(LocalDateTime.now());
        }
        gameService.saveUpdateGame(game);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/devil-code")
    ResponseEntity<String> genDevilCode(@RequestParam String memberIdx) {
        Long idx = Long.valueOf(memberIdx);
        String devilCode = "";
        try {
            devilCode = gameService.genDevilCode(idx);
        } catch (Exception e) {
            devilCode = "";
        }
        return ResponseEntity.ok().body(devilCode);
    }

    @GetMapping("/register/devil-code")
    ResponseEntity<String> saveDevilCode(@RequestParam String memberIdx, @RequestParam String devilCode) {
        Long idx = Long.valueOf(memberIdx);


        String message = "등록이 완료됐습니다.";
        try {
            gameService.saveDevilCode(idx, devilCode);
        } catch (Exception e) {
            message = "유효하지 않은 코드입니다";
        }

        return ResponseEntity.ok().body(message);
    }

}
