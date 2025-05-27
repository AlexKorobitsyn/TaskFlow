package org.alexk.taskfromgoogle.taskflow.service;

import lombok.RequiredArgsConstructor;
import org.alexk.taskfromgoogle.taskflow.dto.AuthRequest;
import org.alexk.taskfromgoogle.taskflow.dto.AuthResponse;
import org.alexk.taskfromgoogle.taskflow.model.User;
import org.alexk.taskfromgoogle.taskflow.repository.UserRepository;
import org.alexk.taskfromgoogle.taskflow.security.JwtUtill;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtill jwtUtil;

    public AuthResponse register(AuthRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }
}
