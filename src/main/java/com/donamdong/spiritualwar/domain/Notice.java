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
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @Column(name = "notice_content")
    private String noticeContent;

    @Column(name = "highlight_level")
    private Integer highlightLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_idx")
    private GameParticipation participation;

    @Column(name = "create_dt")
    private LocalDateTime createDt;

    @Column(name = "expire_dt")
    private LocalDateTime expireDt;

}
