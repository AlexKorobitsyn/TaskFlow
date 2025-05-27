package org.alexk.taskfromgoogle.taskflow.controller;

import lombok.RequiredArgsConstructor;
import org.alexk.taskfromgoogle.taskflow.dto.AuthRequest;
import org.alexk.taskfromgoogle.taskflow.dto.AuthResponse;
import org.alexk.taskfromgoogle.taskflow.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
