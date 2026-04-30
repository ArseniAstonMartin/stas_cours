package by.exam.prep.dto.response;

import java.time.LocalDateTime;

public record TestResponse(
        Long id,
        String title,
        String description,
        Integer timeLimitMinutes,
        String testType,
        Boolean isAdaptive,
        Integer questionCount,
        Long subjectId,
        String subjectName,
        LocalDateTime createdAt
) {}
