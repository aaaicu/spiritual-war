package com.donamdong.spiritualwar.service.push;

import com.donamdong.spiritualwar.domain.GameParticipation;
import com.donamdong.spiritualwar.repository.game.GameParticipationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private GameParticipationRepository gameParticipationRepository;

    @Test
    @DisplayName("알림 구독을 진행한다.")
    public void subscribe() throws Exception {
        //given
        GameParticipation participation = gameParticipationRepository.getById(75L);
        String lastEventId = "";

        //when, then
        Assertions.assertDoesNotThrow(() -> notificationService.subscribe(participation.getIdx(), lastEventId));
    }

    @Test
    @DisplayName("알림 메세지를 전송한다.")
    public void send() throws Exception {
        //given
        GameParticipation participation = gameParticipationRepository.getById(75L);
        String lastEventId = "";
        notificationService.subscribe(participation.getIdx(), lastEventId);

        //when, then
        Assertions.assertDoesNotThrow(() -> notificationService.send(participation.getIdx(),"스터디 신청에 지원하셨습니다."));
    }
}