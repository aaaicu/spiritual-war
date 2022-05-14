package com.donamdong.spiritualwar.repository.push;

import com.donamdong.spiritualwar.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
