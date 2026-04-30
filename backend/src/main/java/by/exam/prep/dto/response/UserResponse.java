package by.exam.prep.dto.response;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String avatarUrl,
        String role,
        LocalDateTime createdAt
) {}
