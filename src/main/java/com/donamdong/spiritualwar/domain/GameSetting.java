package com.donamdong.spiritualwar.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class GameSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    @JsonManagedReference
    private Long idx;

    @Column(name = "total_round")
    private Integer totalRound;

    @Column
    private Integer afternoonMinute;

    @Column
    private Integer nightMinute;

    @Column
    private Integer holidayNightMinute;

    @Column
    private Integer citizen;

    @Column
    private Integer devil;

    @Column
    private LocalDateTime createDt;

}
