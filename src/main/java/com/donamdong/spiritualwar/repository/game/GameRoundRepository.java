package com.donamdong.spiritualwar.repository.game;

import com.donamdong.spiritualwar.domain.GameRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRoundRepository extends JpaRepository<GameRound, Long> {
}
