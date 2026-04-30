package by.exam.prep.service;

import by.exam.prep.dto.response.WeaknessResponse;
import by.exam.prep.entity.WeaknessRecord;
import by.exam.prep.repository.WeaknessRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeaknessService {

    private final WeaknessRecordRepository weaknessRecordRepository;

    public List<WeaknessResponse> getUserWeaknesses(Long userId) {
        return weaknessRecordRepository.findWeakestTopics(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<WeaknessResponse> getCriticalWeaknesses(Long userId) {
        return weaknessRecordRepository.findCriticalWeaknesses(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    private WeaknessResponse toResponse(WeaknessRecord wr) {
        return new WeaknessResponse(wr.getId(), wr.getTopic().getId(),
                wr.getTopic().getName(), wr.getTopic().getSubject().getName(),
                wr.getTotalAttempts(), wr.getCorrectAttempts(), wr.getAccuracyRate(),
                wr.getWeaknessLevel().name(), wr.getLastPracticedAt());
    }
}
