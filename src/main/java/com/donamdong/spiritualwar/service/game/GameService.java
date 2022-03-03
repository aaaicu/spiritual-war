package com.donamdong.spiritualwar.service.game;

import com.donamdong.spiritualwar.domain.*;
import com.donamdong.spiritualwar.repository.game.DevilCodeRepository;
import com.donamdong.spiritualwar.repository.game.GameParticipationRepository;
import com.donamdong.spiritualwar.repository.game.GameRepository;
import com.donamdong.spiritualwar.repository.game.GameSettingRepository;
import com.donamdong.spiritualwar.util.DevilCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final GameSettingRepository gameSettingRepository;
    private final GameParticipationRepository gameParticipationRepository;
    private final GameRoundService gameRoundService;
    private final DevilCodeRepository devilCodeRepository;

    private final DevilCodeGenerator devilCodeGenerator;



    public Game openGame(GameSetting gameSetting, List<GameRound> gameRounds) {
        gameSetting.setCreateDt(LocalDateTime.now());

        GameSetting savedSetting = gameSettingRepository.save(gameSetting);
        Game game = Game.builder().gameSetting(savedSetting).createDt(LocalDateTime.now()).build();
        gameRepository.save(game);
        gameRounds.forEach(e -> {
            e.setGame(game);
            gameRoundService.saveUpdateGameRound(e);
        });
        return game;
    }

    public List<Game> fetchGames() {
        return gameRepository.findAllGameNotEnd();
    }


    public Game getGame(Long gameIdx) {
        return gameRepository.findById(gameIdx).orElseGet(Game::new);
    }
    public void deleteGame(Long gameIdx) {
        gameRepository.deleteById(gameIdx);
    }
    public Game saveUpdateGame(Game game) {
        return gameRepository.save(game);
    }

    public String genDevilCode(Long participationIdx ) {



        GameParticipation devil = gameParticipationRepository.getById(participationIdx);
        Optional<DevilCode> isRegistered = devilCodeRepository.findByParticipationAndRegisterYnIsTrue(devil);
        if (isRegistered.isPresent()) {
            throw new RuntimeException();
        }

        String code = devilCodeGenerator.excuteGenerate();
        devilCodeRepository.save(DevilCode.builder()
                .participation(devil)
                .devilCode(code)
                .expireDt(LocalDateTime.now().plusMinutes(5))
                .build());

        return code;
    }

    public void saveDevilCode(Long hiredMember, String code) {

        DevilCode devilCode = devilCodeRepository.findByDevilCode(code).orElseThrow();


        GameParticipation devil = gameParticipationRepository.findById(devilCode.getParticipation().getIdx()).orElseThrow();

        Optional<DevilCode> isRegistered = devilCodeRepository.findByParticipationAndRegisterYnIsTrue(devil);
        if (isRegistered.isPresent()) {
            throw new RuntimeException();
        }


        devilCode.setRegisterYn(true);


        GameParticipation gameParticipation = gameParticipationRepository.findById(hiredMember).orElseThrow();

        if (gameParticipation.getHiredDt() != null) {
            throw new RuntimeException("이미 등록한 사용자:");
        }

        gameParticipation.setHireDevil(devil);
        gameParticipation.setHiredDt(LocalDateTime.now());

        devilCodeRepository.save(devilCode);
        gameParticipationRepository.save(gameParticipation);
    }
}
