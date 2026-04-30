package by.exam.prep.dto.response;

public record TopicResponse(
        Long id,
        String name,
        String description,
        Integer orderIndex,
        Long subjectId,
        Long parentTopicId
) {}
