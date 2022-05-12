package com.donamdong.spiritualwar.web.controller;

import com.donamdong.spiritualwar.util.DevilCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final DevilCodeGenerator devilCodeGenerator;

    @GetMapping("/")
    String indexPage(Model model) {
        LocalDateTime targetTime = LocalDateTime.now();
        model.addAttribute("targetTime", targetTime);
        return "index";
    }
}
