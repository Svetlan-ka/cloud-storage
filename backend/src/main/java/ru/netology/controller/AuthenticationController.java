package ru.netology.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.DTO.UserDTO;
import ru.netology.entity.Token;
import ru.netology.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<Token> register(@RequestBody UserDTO request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Token> authenticate(@RequestBody UserDTO request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        service.logout(token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PostMapping("/test")
    public ResponseEntity<?> test(@RequestBody UserDTO request) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
