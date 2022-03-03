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
public class GameRound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_idx")
    private Game game;

    @Column(name = "sorting_order")
    private Integer sortingOrder;

    @Column(name = "holiday_yn")
    private Boolean holidayYn;

    @Column(name = "success_yn")
    private Boolean successYn;

    @Column(name = "start_dt")
    private LocalDateTime startDt;

    @Column(name = "end_dt")
    private LocalDateTime endDt;
}
