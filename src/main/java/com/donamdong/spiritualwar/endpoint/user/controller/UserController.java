package com.donamdong.spiritualwar.endpoint.user.controller;

import com.donamdong.spiritualwar.domain.User;
import com.donamdong.spiritualwar.endpoint.user.dto.request.SignUpRequest;
import com.donamdong.spiritualwar.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    final private UserService userService;

    @PostMapping("/signup")
    ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {

        User user = User.builder()
                .userId(signUpRequest.getUserId())
                .userPassword(signUpRequest.getUserPassword())
                .userName(signUpRequest.getUserName())
                .build();

        try {
            userService.signUp(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("아이디 생성 실패");
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check/signup")
    ResponseEntity<String> signUp(@Valid @NotBlank @NotNull String userId) {
        if (!userService.checkId(userId)) {
            return ResponseEntity.badRequest().body("중복된 아이디입니다.");
        }

        return ResponseEntity.noContent().build();
    }

//
//    @PostMapping("/join")
//    ResponseEntity<String> saveSetting(@RequestBody SaveGameRequest saveGameRequest) {
//
//        SaveGameRequest.builder().build();
//
//        gameService.saveSetting();
//        return ResponseEntity.ok();
//    }



//    @PostMapping("/setting/save")
//    ResponseEntity<GameSetting> saveSetting(@RequestBody SaveGameRequest saveGameRequest) {
//
//        GameSetting setting = GameSetting.builder()
//                .totalRound(saveGameRequest.getTotalRound())
//                .afternoonMinute(saveGameRequest.getAfternoonMinute())
//                .nightMinute(saveGameRequest.getNightMinute())
//                .holidayNightMinute(saveGameRequest.getHolidayNightMinute())
//                .citizen(saveGameRequest.getCitizen())
//                .devil(saveGameRequest.getDevil())
//                .createDt(LocalDateTime.now())
//                .build();
//
//        return ResponseEntity.ok(gameSettingService.saveUpdateGameSetting(setting));
//    }
//
//    @PostMapping("/setting/delete/{gameSettingIdx}")
//    ResponseEntity<String> deleteGameSetting(@PathVariable String gameSettingIdx) {
//        gameSettingService.deleteGameSetting(Long.getLong(gameSettingIdx));
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/setting/{gameSettingIdx}")
//    ResponseEntity<GameSetting> getGameSetting(@PathVariable String gameSettingIdx) {
//        return ResponseEntity.ok(gameSettingService.getGameSetting(Long.getLong(gameSettingIdx)));
//    }

}
