package by.exam.prep.dto.request;

import jakarta.validation.constraints.NotNull;

public record SubmitAnswerRequest(
        @NotNull(message = "Question ID is required")
        Long questionId,

        Long selectedAnswerId,

        String textAnswer,

        Integer timeSpentSeconds
) {}
