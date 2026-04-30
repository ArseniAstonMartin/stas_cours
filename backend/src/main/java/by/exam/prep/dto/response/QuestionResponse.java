package by.exam.prep.dto.response;

import java.util.List;

public record QuestionResponse(
        Long id,
        String text,
        String explanation,
        String questionType,
        String difficulty,
        Integer points,
        String imageUrl,
        Long topicId,
        String topicName,
        List<AnswerResponse> answers
) {
    public record AnswerResponse(
            Long id,
            String text,
            Boolean isCorrect,
            Integer orderIndex
    ) {}
}
