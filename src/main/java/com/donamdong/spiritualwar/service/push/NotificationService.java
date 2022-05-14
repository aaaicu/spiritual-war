package com.donamdong.spiritualwar.service.push;

import com.donamdong.spiritualwar.domain.Game;
import com.donamdong.spiritualwar.domain.GameParticipation;
import com.donamdong.spiritualwar.domain.Notification;
import com.donamdong.spiritualwar.repository.game.GameParticipationRepository;
import com.donamdong.spiritualwar.repository.game.GameRepository;
import com.donamdong.spiritualwar.repository.push.EmitterRepository;
import com.donamdong.spiritualwar.repository.push.NotificationRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private static final Integer DEFAULT_HIGHLIGHT_LEVEL = 0;

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;
    private final GameParticipationRepository gameParticipationRepository;
    private final GameRepository gameRepository;

    public SseEmitter subscribe(Long participationIdx, String lastEventId) {
        String emitterId = makeTimeIncludeParticipationIdx(participationIdx);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        String eventId = makeTimeIncludeParticipationIdx(participationIdx);
        sendNotification(emitter, eventId, emitterId, "EventStream Created. [userId=" + participationIdx + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, participationIdx, emitterId, emitter);
        }

        return emitter;
    }

    public void sendGameParticipants(Long gameIdx, String content) {
        List<GameParticipation> allParticipation = gameParticipationRepository.findAllByGame(gameRepository.getById(gameIdx));
        for (GameParticipation participation : allParticipation) {
            this.send(participation.getIdx(), content);
        }
    }

    public void send(Long participationIdx, String content) {
        GameParticipation targetParticipation = gameParticipationRepository.getById(participationIdx);
        Game targetGame = gameRepository.getById(targetParticipation.getGame().getIdx());

        Notification notification = notificationRepository.save(createNotification(targetGame, targetParticipation, content));

        String receiverIdx = String.valueOf(targetParticipation.getIdx());
        String eventId = receiverIdx + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverIdx);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, notification);
                }
        );
    }

    private Notification createNotification(Game game, GameParticipation participation, String content) {
        return Notification.builder()
                .game(game)
                .participation(participation)
                .noticeContent(content)
                .highlightLevel(DEFAULT_HIGHLIGHT_LEVEL)
                .isRead(false)
                .createDt(LocalDateTime.now())
                .expireDt(LocalDateTime.now().plusMinutes(10))
                .build();
    }



    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }

    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventId, Long memberId, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(memberId));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    private String makeTimeIncludeParticipationIdx(Long participationIdx) {
        return participationIdx + "_" + System.currentTimeMillis();
    }

}
