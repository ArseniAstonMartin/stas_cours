package by.exam.prep.dto.response;

import java.time.LocalDateTime;

public record StudyMaterialResponse(
        Long id,
        String title,
        String content,
        String materialType,
        String externalUrl,
        Long topicId,
        String topicName,
        Integer orderIndex,
        LocalDateTime createdAt
) {}
