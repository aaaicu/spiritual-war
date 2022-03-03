package com.donamdong.spiritualwar.repository.game;

import com.donamdong.spiritualwar.domain.DevilCode;
import com.donamdong.spiritualwar.domain.GameParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DevilCodeRepository extends JpaRepository<DevilCode, Long> {
    Optional<DevilCode> findByDevilCode(String devilCode);

    Optional<DevilCode> findByParticipationAndRegisterYnIsTrue(GameParticipation participation);
}
