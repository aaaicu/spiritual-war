package com.donamdong.spiritualwar.endpoint.views;

import com.donamdong.spiritualwar.domain.Game;
import com.donamdong.spiritualwar.domain.GameParticipation;
import com.donamdong.spiritualwar.service.game.GameParticipationService;
import com.donamdong.spiritualwar.service.game.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/admin")
public class AdminViewController {

    private final GameParticipationService gameParticipationService;
    private final GameService gameService;


    @GetMapping("/game/detail")
    public String gameDetail(HttpSession session, Model model, @RequestParam("gameIdx") Long gameIdx) {
        String redirectUrl = isManager(session);
        if (redirectUrl != null) return redirectUrl;

        List<GameParticipation> gameMembers = gameParticipationService.findGameMemberAll(gameIdx);
        model.addAttribute("gameMembers",gameMembers);
        model.addAttribute("gameIdx", gameIdx);

        return "views/admin/game-detail";
    }

    @GetMapping("/game/list")
    public String gameList(HttpSession session, Model model) {
        String redirectUrl = isManager(session);
        if (redirectUrl != null) return redirectUrl;

        List<Game> gameList= gameService.getGameList();
        model.addAttribute("gameList",gameList);

        return "views/admin/game-list";
    }

    private String isManager(HttpSession session) {
        boolean isManager = "true".equals(session.getAttribute("manager"));
        if (!isManager) {
            return "redirect:/view/home";
        }
        return null;
    }


}
