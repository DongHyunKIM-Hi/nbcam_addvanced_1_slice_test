package org.example.nbcam_addvanced_1.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nbcam_addvanced_1.domain.user.model.request.UpdateUserEmailDto;
import org.example.nbcam_addvanced_1.domain.user.model.dto.UserDto;
import org.example.nbcam_addvanced_1.domain.user.model.request.UpdateUserAgeDto;
import org.example.nbcam_addvanced_1.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PutMapping("/{username}/email")
    public String updateUserEmail(@PathVariable String username, @RequestBody UpdateUserEmailDto dto) {
        userService.updateEmail(username,dto.getEmail());
        return "수정 완료!";
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserInfoByUsername(username));
    }

    @PutMapping("/{username}/age")
    public ResponseEntity<UserDto> updateUserAge(@PathVariable String username, @RequestBody UpdateUserAgeDto dto) {
        return ResponseEntity.ok(userService.updateUserAge(username,dto.getAge()));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok("삭제 성공");
    }
}
