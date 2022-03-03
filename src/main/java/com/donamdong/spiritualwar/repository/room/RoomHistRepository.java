package com.donamdong.spiritualwar.repository.room;

import com.donamdong.spiritualwar.domain.RoomHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomHistRepository extends JpaRepository<RoomHist, Long> {


    List<RoomHist> findByGameIdx(Long gameIdx);

}
