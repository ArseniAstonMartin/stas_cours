package by.exam.prep.dto.response;

import java.time.LocalDateTime;

public record WeaknessResponse(
        Long id,
        Long topicId,
        String topicName,
        String subjectName,
        Integer totalAttempts,
        Integer correctAttempts,
        Double accuracyRate,
        String weaknessLevel,
        LocalDateTime lastPracticedAt
) {}
