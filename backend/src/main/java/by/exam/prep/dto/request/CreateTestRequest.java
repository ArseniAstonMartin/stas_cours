package by.exam.prep.dto.request;

import by.exam.prep.entity.Test;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CreateTestRequest(
        @NotBlank(message = "Title is required")
        String title,

        String description,

        @NotNull(message = "Subject ID is required")
        Long subjectId,

        @Positive(message = "Time limit must be positive")
        Integer timeLimitMinutes,

        Test.TestType testType,

        Boolean isAdaptive,

        Integer questionCount,

        List<Long> questionIds,

        List<Long> topicIds
) {}
