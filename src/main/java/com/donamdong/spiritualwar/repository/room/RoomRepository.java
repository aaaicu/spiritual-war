package com.donamdong.spiritualwar.repository.room;

import com.donamdong.spiritualwar.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
