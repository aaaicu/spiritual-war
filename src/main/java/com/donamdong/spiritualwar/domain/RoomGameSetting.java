package com.donamdong.spiritualwar.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RoomGameSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch=FetchType.LAZY)
    private GameSetting gameSetting;

    @ManyToOne(fetch=FetchType.LAZY)
    private Room room;

    @Column
    private Integer capacity;
}
