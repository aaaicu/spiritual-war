package com.donamdong.spiritualwar.endpoint.views;

import com.donamdong.spiritualwar.domain.GameParticipation;
import com.donamdong.spiritualwar.domain.User;
import com.donamdong.spiritualwar.service.game.GameParticipationService;
import com.donamdong.spiritualwar.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/tool")
public class ToolViewController {

    private final UserService userService;
    private final GameParticipationService gameParticipationService;

    @GetMapping
    public String toolPage(HttpSession session, Model model) {
        Optional<User> user = Optional.ofNullable((User) session.getAttribute("user"));
        User checkedUser;
        if (user.isPresent()) {
            checkedUser = userService.getUser(user.get().getIdx());
            session.setAttribute("user",checkedUser);

            // 참여중 게임 있을 경우
            List<GameParticipation> gameMembers = gameParticipationService.findGameMember(checkedUser.getIdx());
            if (!gameMembers.isEmpty()) {
                model.addAttribute("gameMembers",gameMembers);
                Long gameIdx = gameMembers.get(0).getGame().getIdx();
                GameParticipation gameParticipation = gameMembers.stream().filter(e -> e.getUser().getIdx().equals(checkedUser.getIdx())).findFirst().get();
                model.addAttribute("member",gameParticipation);

                model.addAttribute("participationIdx", gameIdx);



            } else {
                // 참여중 게임 없을 경우
                model.addAttribute("gameMembers", null);
            }
            return "views/tool/tool";
        }
        return "redirect:/view/home";
    }
}
