package org.example.nbcam_addvanced_1.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nbcam_addvanced_1.common.entity.User;
import org.example.nbcam_addvanced_1.common.utils.JwtUtil;
import org.example.nbcam_addvanced_1.domain.user.model.dto.UserDto;
import org.example.nbcam_addvanced_1.domain.user.model.request.LoginRequestDto;
import org.example.nbcam_addvanced_1.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public String login(LoginRequestDto request) {

        String username = request.getUsername();
        String password = request.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.generateToken(user.getUsername(), user.getRole());
    }

    // InitDat 저장용 으로 만든 메서드임
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void updateEmail(String username, String email) {

        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        user.updateEmail(email);

    }

    @Transactional
    public UserDto getUserInfoByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        return UserDto.from(user);
    }

    @Transactional
    public UserDto updateUserAge(String username, int age) {

        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        userRepository.updateAge(username, age);
        user.updateAge(age);

        return UserDto.from(user);

    }

    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }
}
