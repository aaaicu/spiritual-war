package com.donamdong.spiritualwar.endpoint.game.dto.request;

import com.donamdong.spiritualwar.domain.GameRound;
import com.donamdong.spiritualwar.domain.GameSetting;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OpenGameRequest {
    private GameSetting gameSetting;
    private List<GameRound> gameRounds;
}













