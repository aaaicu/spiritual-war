package com.donamdong.spiritualwar.endpoint.game.dto.request;

import com.donamdong.spiritualwar.domain.GameSetting;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenGameRequest {
    private GameSetting gameSetting;
}













