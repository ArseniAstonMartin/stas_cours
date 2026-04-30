package by.exam.prep.dto.response;

import java.util.List;

public record SubjectResponse(
        Long id,
        String name,
        String description,
        String iconUrl,
        List<TopicResponse> topics
) {}
