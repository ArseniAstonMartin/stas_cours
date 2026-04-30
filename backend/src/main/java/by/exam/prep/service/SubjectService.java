package by.exam.prep.service;

import by.exam.prep.dto.response.SubjectResponse;
import by.exam.prep.dto.response.TopicResponse;
import by.exam.prep.entity.Subject;
import by.exam.prep.exception.ResourceNotFoundException;
import by.exam.prep.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Cacheable("subjects")
    public List<SubjectResponse> getAllSubjects() {
        return subjectRepository.findAllWithTopics().stream()
                .map(this::toResponse)
                .toList();
    }

    public SubjectResponse getSubjectById(Long id) {
        Subject subject = subjectRepository.findByIdWithTopics(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject", "id", id));
        return toResponse(subject);
    }

    @Transactional
    public SubjectResponse createSubject(String name, String description, String iconUrl) {
        Subject subject = Subject.builder()
                .name(name)
                .description(description)
                .iconUrl(iconUrl)
                .build();
        subject = subjectRepository.save(subject);
        return toResponse(subject);
    }

    private SubjectResponse toResponse(Subject subject) {
        List<TopicResponse> topics = Optional.ofNullable(subject.getTopics())
                .map(t -> t.stream()
                        .map(topic -> new TopicResponse(
                                topic.getId(), topic.getName(), topic.getDescription(),
                                topic.getOrderIndex(), subject.getId(),
                                topic.getParentTopic() != null ? topic.getParentTopic().getId() : null))
                        .toList())
                .orElse(List.of());

        return new SubjectResponse(subject.getId(), subject.getName(),
                subject.getDescription(), subject.getIconUrl(), topics);
    }
}
