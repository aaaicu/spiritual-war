package com.donamdong.spiritualwar.repository.game;

import com.donamdong.spiritualwar.domain.Game;
import com.donamdong.spiritualwar.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("select p.game from GameParticipation p inner join p.game  g where p.user = :user and g.endDt is null order by p.createDt desc ")
    List<Game> findGameParticipationByUserIdx(User user);

    @Query("select g from Game g where g.endDt is null and g.startDt is null order by g.idx asc ")
    List<Game> findAllGameNotEnd();
}
