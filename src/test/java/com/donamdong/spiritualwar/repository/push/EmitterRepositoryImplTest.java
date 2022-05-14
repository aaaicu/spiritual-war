package com.donamdong.spiritualwar.repository.push;

import com.donamdong.spiritualwar.domain.Game;
import com.donamdong.spiritualwar.domain.GameParticipation;
import com.donamdong.spiritualwar.domain.Notification;
import com.donamdong.spiritualwar.repository.game.GameParticipationRepository;
import com.donamdong.spiritualwar.repository.game.GameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.Map;

@SpringBootTest
class EmitterRepositoryImplTest {

    @Autowired
    private GameParticipationRepository gameParticipationRepository;
    @Autowired
    private GameRepository gameRepository;

    private final EmitterRepository emitterRepository = new EmitterRepositoryImpl();
    private final Long DEFAULT_TIMEOUT = 60L * 1000L * 60L;

    @Test
    @DisplayName("새로운 Emitter를 추가한다.")
    public void save() throws Exception {
        //given
        Long memberId = 1L;
        String emitterId =  memberId + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        //when, then
        Assertions.assertDoesNotThrow(() -> emitterRepository.save(emitterId, sseEmitter));
    }

    @Test
    @DisplayName("수신한 이벤트를 캐시에 저장한다.")
    public void saveEventCache() throws Exception {

        //given
        Long memberId = 1L;
        String eventCacheId =  memberId + "_" + System.currentTimeMillis();
        Notification notification = Notification.builder()
                .createDt(LocalDateTime.now())
                .expireDt(LocalDateTime.now().plusMinutes(10))
                .game(gameRepository.getById(1L))
                .noticeContent("test")
                .highlightLevel(0)
                .participation(gameParticipationRepository.getById(75L))
                .isRead(false).build();

        //when, then
        Assertions.assertDoesNotThrow(() -> emitterRepository.saveEventCache(eventCacheId, notification));
    }

    @Test
    @DisplayName("어떤 회원이 접속한 모든 Emitter를 찾는다")
    public void findAllEmitterStartWithByMemberId() throws Exception {
        //given
        Long memberId = 75L;
        String emitterId1 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId2 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId3 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId3, new SseEmitter(DEFAULT_TIMEOUT));


        //when
        Map<String, SseEmitter> ActualResult = emitterRepository.findAllEmitterStartWithByMemberId(String.valueOf(memberId));

        //then
        Assertions.assertEquals(3, ActualResult.size());
    }

    @Test
    @DisplayName("어떤 회원에게 수신된 이벤트를 캐시에서 모두 찾는다.")
    public void findAllEventCacheStartWithByMemberId() throws Exception {
        //given
        Long memberId = 75L;
        String eventCacheId1 =  memberId + "_" + System.currentTimeMillis();
        Notification notification1 = Notification.builder()
                .createDt(LocalDateTime.now())
                .expireDt(LocalDateTime.now().plusMinutes(10))
                .game(gameRepository.getById(75L))
                .noticeContent("test111")
                .highlightLevel(0)
                .participation(gameParticipationRepository.getById(75L))
                .isRead(false).build();

        emitterRepository.saveEventCache(eventCacheId1, notification1);

        Thread.sleep(100);
        String eventCacheId2 =  memberId + "_" + System.currentTimeMillis();
        Notification notification2 = Notification.builder()
                .createDt(LocalDateTime.now())
                .expireDt(LocalDateTime.now().plusMinutes(10))
                .game(gameRepository.getById(75L))
                .noticeContent("test222")
                .highlightLevel(0)
                .participation(gameParticipationRepository.getById(75L))
                .isRead(false).build();
        emitterRepository.saveEventCache(eventCacheId2, notification2);

        Thread.sleep(100);
        String eventCacheId3 =  memberId + "_" + System.currentTimeMillis();
        Notification notification3 = Notification.builder()
                .createDt(LocalDateTime.now())
                .expireDt(LocalDateTime.now().plusMinutes(10))
                .game(gameRepository.getById(75L))
                .noticeContent("tes333")
                .highlightLevel(0)
                .participation(gameParticipationRepository.getById(75L))
                .isRead(false).build();
        emitterRepository.saveEventCache(eventCacheId3, notification3);

        //when
        Map<String, Object> ActualResult = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(memberId));

        //then
        Assertions.assertEquals(3, ActualResult.size());
    }

    @Test
    @DisplayName("ID를 통해 Emitter를 Repository에서 제거한다.")
    public void deleteById() throws Exception {
        //given
        Long memberId = 75L;
        String emitterId =  memberId + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        //when
        emitterRepository.save(emitterId, sseEmitter);
        emitterRepository.deleteById(emitterId);

        //then
        Assertions.assertEquals(0, emitterRepository.findAllEmitterStartWithByMemberId(emitterId).size());
    }

    @Test
    @DisplayName("저장된 모든 Emitter를 제거한다.")
    public void deleteAllEmitterStartWithId() throws Exception {
        //given
        Long memberId = 75L;
        String emitterId1 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId2 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));

        //when
        emitterRepository.deleteAllEmitterStartWithId(String.valueOf(memberId));

        //then
        Assertions.assertEquals(0, emitterRepository.findAllEmitterStartWithByMemberId(String.valueOf(memberId)).size());
    }

    @Test
    @DisplayName("수신한 이벤트를 캐시에 저장한다.")
    public void deleteAllEventCacheStartWithId() throws Exception {
        //given
        Long memberId = 1L;
        String eventCacheId1 =  memberId + "_" + System.currentTimeMillis();
        Notification notification1 = Notification.builder()
                .createDt(LocalDateTime.now())
                .expireDt(LocalDateTime.now().plusMinutes(10))
                .game(gameRepository.getById(75L))
                .noticeContent("tes1111")
                .highlightLevel(0)
                .participation(gameParticipationRepository.getById(75L))
                .isRead(false).build();
        emitterRepository.saveEventCache(eventCacheId1, notification1);

        Thread.sleep(100);
        String eventCacheId2 =  memberId + "_" + System.currentTimeMillis();
        Notification notification2 = Notification.builder()
                .createDt(LocalDateTime.now())
                .expireDt(LocalDateTime.now().plusMinutes(10))
                .game(gameRepository.getById(75L))
                .noticeContent("tes22222")
                .highlightLevel(0)
                .participation(gameParticipationRepository.getById(75L))
                .isRead(false).build();
        emitterRepository.saveEventCache(eventCacheId2, notification2);

        //when
        emitterRepository.deleteAllEventCacheStartWithId(String.valueOf(memberId));

        //then
        Assertions.assertEquals(0, emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(memberId)).size());
    }
}