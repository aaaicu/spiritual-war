package com.donamdong.spiritualwar.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RoomHist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch=FetchType.LAZY)
    private GameRound gameRound;

    @ManyToOne(fetch=FetchType.LAZY)
    private Game game;

    @ManyToOne(fetch=FetchType.LAZY)
    private GameParticipation participation;

    @ManyToOne(fetch=FetchType.LAZY)
    private Room room;
}
