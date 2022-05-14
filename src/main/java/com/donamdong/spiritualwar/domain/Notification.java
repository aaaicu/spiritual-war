package com.donamdong.spiritualwar.domain;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "idx")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "game_idx")
    private Game game;

    @Column(name = "notice_content")
    private String noticeContent;

    @Column(name = "highlight_level")
    private Integer highlightLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_idx")
    private GameParticipation participation;

    @Column(nullable = false)
    private Boolean isRead;

    @Column(name = "create_dt")
    private LocalDateTime createDt;

    @Column(name = "expire_dt")
    private LocalDateTime expireDt;

}
