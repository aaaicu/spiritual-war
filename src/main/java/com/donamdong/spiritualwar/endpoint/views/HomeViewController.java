package com.donamdong.spiritualwar.endpoint.views;

import com.donamdong.spiritualwar.domain.GameParticipation;
import com.donamdong.spiritualwar.domain.User;
import com.donamdong.spiritualwar.endpoint.user.dto.request.SignInRequest;
import com.donamdong.spiritualwar.service.game.GameParticipationService;
import com.donamdong.spiritualwar.service.game.GameService;
import com.donamdong.spiritualwar.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/home")
public class HomeViewController {

    private final UserService userService;
    private final GameService gameService;
    private final GameParticipationService gameParticipationService;

    @GetMapping
    public String signInPage(HttpSession session, Model model, RedirectAttributes redirectAttributes, @RequestParam(required = false) String result) {

        Optional<User> user = getUserInfo(session);
        if (user.isEmpty()) {
            flushLoginData(session, model, redirectAttributes, result);
            return "views/home/signin";
        }

        if (user.get().getManagerYn()) {
            setSessionManager(session, user.get());
            return "redirect:/view/admin/game/list";

        } else {
            setSessionNormalUser(session, user.get());
            setModelUserGameInfo(model, user.get());
            return "views/home/main";
        }
    }


    @PostMapping("/signin")
    ModelAndView signIn(HttpServletRequest request,
                        HttpSession session,
                        @RequestParam("id") String id,
                        @RequestParam("pw")String pw,
                        RedirectAttributes redirectAttributes) {

        SignInRequest signInRequest = SignInRequest.builder()
                .userId(id)
                .userPassword(pw)
                .build();

        Optional<User> user = userService.signIn(signInRequest);

        if (user.isEmpty()) {
            session.setAttribute("user", null);
            session.setAttribute("manager", null);
            redirectAttributes.addFlashAttribute("msg", false);
            return new ModelAndView("redirect:/view/home?result=false");
        } else {
            session.setAttribute("user", user.get());
        }
        return new ModelAndView("redirect:/view/home");
    }

    @GetMapping("/signout")
    String signOut(HttpSession session, RedirectAttributes redirectAttributes) {
        session.setAttribute("user", null);
        session.setAttribute("manager", null);
        redirectAttributes.addFlashAttribute("msg", false);
        return "redirect:/view/home";
    }


    @GetMapping("/signup-page")
    String signUpPage(HttpSession session, RedirectAttributes redirectAttributes) {
        return "views/home/signup";
    }


    @PostMapping("/signup")
    ModelAndView signUp(HttpServletRequest request,
                        HttpSession session,
                        @RequestParam("id") String id,
                        @RequestParam("pw")String pw,
                        @RequestParam("name")String name,
                        RedirectAttributes redirectAttributes) {

        userService.signUp(User.builder().userName(name).userPassword(pw).userId(id).build());
        return new ModelAndView("redirect:/view/home");
    }


    private void flushLoginData(HttpSession session, Model model, RedirectAttributes redirectAttributes, String result) {
        session.setAttribute("user", null);
        redirectAttributes.addFlashAttribute("msg", false);
        model.addAttribute("result", result);
    }

    private void setModelUserGameInfo(Model model, User user) {

        List<GameParticipation> gameMembers = gameParticipationService.findGameMember(user.getIdx());

        if (!gameMembers.isEmpty()) {
            // 참여중 게임 있을 경우
            model.addAttribute("gameMembers",gameMembers);

            Long gameIdx = gameMembers.get(0).getGame().getIdx();
            model.addAttribute("gameIdx", gameIdx);
            model.addAttribute("participationIdx", gameIdx);

        } else {
            // 참여중 게임 없을 경우
            model.addAttribute("gameInfo", gameService.fetchCanJoinGameList());
        }
    }

    private void setSessionNormalUser(HttpSession session, User user) {
        session.setAttribute("user",user);
    }

    private void setSessionManager(HttpSession session, User user) {
        session.setAttribute("manager", "true");
        session.setAttribute("user",user);
    }

    private Optional<User> getUserInfo(HttpSession session) {
        Optional<User> user = Optional.ofNullable((User) session.getAttribute("user"));
        return user.map(u -> userService.getUser(u.getIdx()));

    }

}
