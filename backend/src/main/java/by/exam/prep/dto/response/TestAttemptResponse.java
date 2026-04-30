package by.exam.prep.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record TestAttemptResponse(
        Long id,
        Long testId,
        String testTitle,
        Double score,
        Double maxScore,
        Double percentage,
        Integer timeSpentSeconds,
        String status,
        LocalDateTime startedAt,
        LocalDateTime completedAt,
        List<UserAnswerResponse> userAnswers
) {
    public record UserAnswerResponse(
            Long id,
            Long questionId,
            String questionText,
            Long selectedAnswerId,
            String textAnswer,
            Boolean isCorrect,
            Integer timeSpentSeconds
    ) {}
}
