package listener;

import com.donamdong.spiritualwar.service.push.NotificationService;
import listener.dto.NotificationRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationListener {
    private final NotificationService notificationService;


    @TransactionalEventListener
    @Async
    public void handleNotification(NotificationRequestDTO notificationRequestDTO) {
        notificationService.send(notificationRequestDTO.getParticipationIdx(), notificationRequestDTO.getContent());
    }
}
