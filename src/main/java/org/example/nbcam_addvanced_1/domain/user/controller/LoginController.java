package org.example.nbcam_addvanced_1.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.nbcam_addvanced_1.domain.user.model.request.LoginRequestDto;
import org.example.nbcam_addvanced_1.domain.user.model.response.LoginResponseDto;
import org.example.nbcam_addvanced_1.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {

        String token  = userService.login(request);

        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
