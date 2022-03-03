package com.donamdong.spiritualwar.repository.room;

import com.donamdong.spiritualwar.domain.RoomGameSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomGameSettingRepository extends JpaRepository<RoomGameSetting, Long> {

}
