package by.exam.prep.service;

import by.exam.prep.dto.request.LoginRequest;
import by.exam.prep.dto.request.RegisterRequest;
import by.exam.prep.dto.response.AuthResponse;
import by.exam.prep.dto.response.UserResponse;
import by.exam.prep.entity.User;
import by.exam.prep.exception.DuplicateResourceException;
import by.exam.prep.exception.ResourceNotFoundException;
import by.exam.prep.security.JwtTokenProvider;
import by.exam.prep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("User with email " + request.email() + " already exists");
        }

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .firstName(request.firstName())
                .lastName(request.lastName())
                .role(User.Role.STUDENT)
                .provider(User.AuthProvider.LOCAL)
                .build();

        user = userRepository.save(user);
        log.info("New user registered: {}", user.getEmail());

        return generateAuthResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        log.info("User logged in: {}", user.getEmail());
        return generateAuthResponse(user);
    }

    public AuthResponse refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        Long userId = jwtTokenProvider.getUserIdFromToken(refreshToken)
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return generateAuthResponse(user);
    }

    private AuthResponse generateAuthResponse(User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(
                user.getId(), user.getEmail(), user.getRole().name());
        String refreshToken = jwtTokenProvider.generateRefreshToken(
                user.getId(), user.getEmail(), user.getRole().name());

        UserResponse userResponse = new UserResponse(
                user.getId(), user.getEmail(), user.getFirstName(),
                user.getLastName(), user.getAvatarUrl(), user.getRole().name(),
                user.getCreatedAt());

        return new AuthResponse(accessToken, refreshToken,
                jwtTokenProvider.getAccessTokenExpiration(), userResponse);
    }
}
