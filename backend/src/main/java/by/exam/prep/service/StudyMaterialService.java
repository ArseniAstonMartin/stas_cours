package by.exam.prep.service;

import by.exam.prep.dto.response.StudyMaterialResponse;
import by.exam.prep.entity.StudyMaterial;
import by.exam.prep.exception.ResourceNotFoundException;
import by.exam.prep.repository.StudyMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyMaterialService {

    private final StudyMaterialRepository studyMaterialRepository;

    public List<StudyMaterialResponse> getMaterialsByTopic(Long topicId) {
        return studyMaterialRepository.findByTopicIdOrderByOrderIndexAsc(topicId).stream()
                .map(this::toResponse)
                .toList();
    }

    public StudyMaterialResponse getMaterialById(Long id) {
        StudyMaterial material = studyMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StudyMaterial", "id", id));
        return toResponse(material);
    }

    private StudyMaterialResponse toResponse(StudyMaterial m) {
        return new StudyMaterialResponse(m.getId(), m.getTitle(), m.getContent(),
                m.getMaterialType().name(), m.getExternalUrl(), m.getTopic().getId(),
                m.getTopic().getName(), m.getOrderIndex(), m.getCreatedAt());
    }
}
