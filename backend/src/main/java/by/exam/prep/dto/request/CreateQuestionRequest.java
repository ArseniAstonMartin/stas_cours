package by.exam.prep.dto.request;

import by.exam.prep.entity.Question;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateQuestionRequest(
        @NotBlank(message = "Question text is required")
        String text,

        String explanation,

        @NotNull(message = "Question type is required")
        Question.QuestionType questionType,

        Question.Difficulty difficulty,

        Integer points,

        @NotNull(message = "Topic ID is required")
        Long topicId,

        String imageUrl,

        @NotEmpty(message = "At least one answer is required")
        @Valid
        List<AnswerRequest> answers
) {
    public record AnswerRequest(
            @NotBlank(message = "Answer text is required")
            String text,
            Boolean isCorrect,
            Integer orderIndex
    ) {}
}
