package com.donamdong.spiritualwar.endpoint.push;

import com.donamdong.spiritualwar.domain.GameParticipation;
import com.donamdong.spiritualwar.repository.game.GameParticipationRepository;
import com.donamdong.spiritualwar.repository.game.GameRepository;
import com.donamdong.spiritualwar.service.push.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class NotificationControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private GameParticipationRepository gameParticipationRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    NotificationController notificationController;

    @BeforeEach
    void beforeEach() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("SSE에 연결을 진행한다.")
    public void subscribe() throws Exception {
        //given
        GameParticipation participation = gameParticipationRepository.getById(75L);

        //when, then
        mockMvc.perform(get("/notification/subscribe/{participationIdx}",participation.getIdx()))
                .andDo(print())
                .andExpect(status().isOk());
    }

}