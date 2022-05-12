package com.donamdong.spiritualwar.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idx")
public class GameSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
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
