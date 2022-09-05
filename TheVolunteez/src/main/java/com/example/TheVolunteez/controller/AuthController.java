package com.example.TheVolunteez.controller;

import com.example.TheVolunteez.dto.LoginDto;
import com.example.TheVolunteez.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

}
