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
public class DevilCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_idx")
    private GameParticipation participation;

    @Column(name = "devil_code")
    private String devilCode;

    @Column(name = "register_yn")
    private Boolean registerYn;

    @Column(name = "expire_dt")
    private LocalDateTime expireDt;

    @Column(name = "create_dt")
    private LocalDateTime createDt;
}
