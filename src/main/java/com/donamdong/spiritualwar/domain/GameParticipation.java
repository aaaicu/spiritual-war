package com.donamdong.spiritualwar.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class GameParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "game_idx")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "best_friend_idx")
    @OneToMany(mappedBy = "bestFriend")
    private GameParticipation bestFriend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fan_idx")
    @OneToMany(mappedBy = "fan")
    private GameParticipation fan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "holy_watching_idx")
    @OneToMany(mappedBy = "fan")
    private GameParticipation holyWatching;

    @Enumerated(EnumType.STRING)
    @Column(name = "participation_role")
    private Role participationRole;

    @Column(name = "hired_dt")
    private LocalDateTime hiredDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hire_devil_idx")
    @OneToMany(mappedBy = "hireDevil")
    private GameParticipation hireDevil;

    @Column(name = "create_dt")
    private LocalDateTime createDt;
}
