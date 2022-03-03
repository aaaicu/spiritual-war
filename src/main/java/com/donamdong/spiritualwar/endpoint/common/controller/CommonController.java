package com.donamdong.spiritualwar.endpoint.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/common")
public class CommonController {

    @GetMapping("/now")
    ResponseEntity<String> fetchNowDateTime() {
        LocalDateTime targetTime = LocalDateTime.now();
        return ResponseEntity.ok().body(targetTime.toString());
    }

}
