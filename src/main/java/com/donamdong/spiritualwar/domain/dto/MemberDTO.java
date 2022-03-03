package com.donamdong.spiritualwar.domain.dto;

import com.donamdong.spiritualwar.domain.Game;
import com.donamdong.spiritualwar.domain.GameParticipation;
import com.donamdong.spiritualwar.domain.Role;
import com.donamdong.spiritualwar.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MemberDTO {
    private Long idx;
    private Game game;
    private User user;
    private GameParticipation bestFriend;
    private GameParticipation fan;
    private GameParticipation holyWatching;
    private Role participationRole;
    private LocalDateTime hiredDt;
    private GameParticipation hireDevil;
    private LocalDateTime createDt;
}
