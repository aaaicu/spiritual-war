package com.donamdong.spiritualwar.service.user;

import com.donamdong.spiritualwar.domain.Game;
import com.donamdong.spiritualwar.domain.User;
import com.donamdong.spiritualwar.endpoint.user.dto.request.SignInRequest;
import com.donamdong.spiritualwar.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void signUp(User user) throws RuntimeException {
        if (!this.checkId(user.getUserId())) {
            throw new RuntimeException("아이디 중복 ");
        }
        user.setManagerYn(false);
        userRepository.save(user);
    }

    // 중복아이디 체크
    public Boolean checkId(String id) {
        return userRepository.findFirstByUserId(id).isEmpty();
    }


    public Optional<User> signin(SignInRequest signInRequest) {
        return userRepository.findFirstByUserIdAndUserPassword(signInRequest.getUserId(), signInRequest.getUserPassword());
    }

    public User getUser(Long userIdx) {
        return userRepository.findById(userIdx).orElseThrow(() -> new RuntimeException("사용자 없음"));
    }

}
