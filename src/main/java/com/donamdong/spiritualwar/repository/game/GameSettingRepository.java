package com.donamdong.spiritualwar.repository.game;

import com.donamdong.spiritualwar.domain.GameSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSettingRepository extends JpaRepository<GameSetting, Long> {
}
