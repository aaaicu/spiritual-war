package com.donamdong.spiritualwar.repository.game;

import com.donamdong.spiritualwar.domain.Game;
import com.donamdong.spiritualwar.domain.GameParticipation;
import com.donamdong.spiritualwar.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameParticipationRepository extends JpaRepository<GameParticipation, Long> {

    @Query(" select p from GameParticipation p join fetch p.user " +
            "left outer join fetch p.bestFriend " +
            "left outer join fetch p.fan " +
            "left outer join fetch p.hireDevil " +
            "left outer join fetch p.holyWatching " +
            "where p.game = :game order by p.createDt")
    List<GameParticipation> findAllUserByGameJoin(Game game);

    GameParticipation findGameParticipationByGameAndUser(Game game, User user);

    List<GameParticipation> findAllByGame(Game game);
}
