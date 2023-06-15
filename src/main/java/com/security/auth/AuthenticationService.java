package com.security.auth;

import com.security.config.JwtService;
import com.security.model.Role;
import com.security.model.User;
import com.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDto register(ReqisterRequestDto request) {
        var user = User.builder()
                .email(request.email)
                .firstname(request.firstname)
                .lastname(request.lastname)
                .password(passwordEncoder.encode(request.getPassword()))
                .schoolRole(request.schoolRole)
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return AuthenticationResponseDto.builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("user not Found"));
        return AuthenticationResponseDto.builder()
                .token(jwtService.generateToken(user))
                .build();
    }
}
