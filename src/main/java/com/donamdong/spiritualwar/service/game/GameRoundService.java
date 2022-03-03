package com.donamdong.spiritualwar.service.game;

import com.donamdong.spiritualwar.domain.GameRound;
import com.donamdong.spiritualwar.repository.game.GameRoundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GameRoundService {
    private final GameRoundRepository gameRoundRepository;

    public GameRound getGameRound(Long gameSettingIdx) {
        return gameRoundRepository.findById(gameSettingIdx).orElseGet(GameRound::new);
    }

    public void deleteGameRound(Long gameSettingIdx) {
        gameRoundRepository.deleteById(gameSettingIdx);
    }

    public GameRound saveUpdateGameRound(GameRound gameRound) {
        return gameRoundRepository.save(gameRound);
    }

}
