package com.donamdong.spiritualwar.service.game;

import com.donamdong.spiritualwar.domain.GameSetting;
import com.donamdong.spiritualwar.repository.game.GameSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GameSettingService {
    private final GameSettingRepository gameSettingRepository;


    public GameSetting getGameSetting(Long gameSettingIdx) {
        return gameSettingRepository.findById(gameSettingIdx).orElseGet(GameSetting::new);
    }

    public void deleteGameSetting(Long gameSettingIdx) {
        gameSettingRepository.deleteById(gameSettingIdx);
    }

    public GameSetting saveUpdateGameSetting(GameSetting gameSetting) {
        return gameSettingRepository.save(gameSetting);
    }

}
