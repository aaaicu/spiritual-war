package com.donamdong.spiritualwar.endpoint.push;

import com.donamdong.spiritualwar.service.push.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/subscribe/{participationIdx}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable Long participationIdx,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {

        return notificationService.subscribe(participationIdx, lastEventId);
    }

 
    @PostMapping(value = "/send/{gameIdx}/participants")
    public ResponseEntity<String> sendGameParticipants(@PathVariable Long gameIdx, @RequestBody String content) {

        notificationService.sendGameParticipants(gameIdx, content);
        return ResponseEntity.noContent().build();
    }
}

