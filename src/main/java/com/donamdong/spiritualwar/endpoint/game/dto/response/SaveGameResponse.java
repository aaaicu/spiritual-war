package com.donamdong.spiritualwar.endpoint.game.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveGameResponse {
    private Integer totalRound;
    private Integer afternoonMinute;
    private Integer nightMinute;
    private Integer holidayNightMinute;
    private Integer citizen;
    private Integer devil;


}
