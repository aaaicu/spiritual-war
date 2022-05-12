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
        Optional<User> user = Optional.ofNullable((User) session.getAttribute("user"));

        User checkedUser;
        if (user.isPresent()) {
            checkedUser = userService.getUser(user.get().getIdx());

            //정상로그인
            if (checkedUser.getUserId() != null && !checkedUser.getUserId().isEmpty()) {

                // 관리자
                if (checkedUser.getManagerYn()) {
                    session.setAttribute("manager", "true");

                    //하드코딩
                    List<GameParticipation> gameMembers = gameParticipationService.findGameMemberAll(1L);



                    //관리자일 경우
                    model.addAttribute("gameMembers",gameMembers);
                    return "views/home/admin";
                }

                session.setAttribute("user",checkedUser);

                // 참여중 게임 있을 경우
                List<GameParticipation> gameMembers = gameParticipationService.findGameMember(checkedUser.getIdx());

                if (!gameMembers.isEmpty()) {
                    model.addAttribute("gameMembers",gameMembers);
                } else {
                    // 참여중 게임 없을 경우
                    model.addAttribute("gameInfo", gameService.fetchCanJoinGameList());
                }

                return "views/home/main";

            } else {
                session.setAttribute("user", null);
                redirectAttributes.addFlashAttribute("msg", false);
            }
        }
        if (result !=null) {
            model.addAttribute("result", result);

        }
        return "views/home/signin";
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
}
