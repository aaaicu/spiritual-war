package com.donamdong.spiritualwar.endpoint.push;

import com.donamdong.spiritualwar.service.push.PushService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/push")
public class PushController {
    private static final Map<String, SseEmitter> CLIENTS = new ConcurrentHashMap<>();

    private final PushService PushService;

    @GetMapping(value = "/subscribe/{id}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable Long id,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return PushService.subscribe(id, lastEventId);
    }
}

