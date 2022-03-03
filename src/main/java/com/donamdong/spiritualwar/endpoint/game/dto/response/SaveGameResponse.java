package com.donamdong.spiritualwar.endpoint.game.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SaveGameResponse {
    private Integer totalRound;
    private Integer afternoonMinute;
    private Integer nightMinute;
    private Integer holidayNightMinute;
    private Integer citizen;
    private Integer devil;


}
