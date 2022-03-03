package com.donamdong.spiritualwar.endpoint.game.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SaveGameRequest {
    private Integer totalRound;
    private Integer afternoonMinute;
    private Integer nightMinute;
    private Integer holidayNightMinute;
    private Integer citizen;
    private Integer devil;
}
