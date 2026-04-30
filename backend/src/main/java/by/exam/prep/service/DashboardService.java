package by.exam.prep.service;

import by.exam.prep.dto.response.DashboardResponse;
import by.exam.prep.dto.response.WeaknessResponse;
import by.exam.prep.entity.TestAttempt;
import by.exam.prep.repository.TestAttemptRepository;
import by.exam.prep.repository.UserAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final TestAttemptRepository testAttemptRepository;
    private final UserAnswerRepository userAnswerRepository;
    private final WeaknessService weaknessService;

    public DashboardResponse getDashboard(Long userId) {
        int totalTests = (int) testAttemptRepository.countByUserIdAndStatus(
                userId, TestAttempt.AttemptStatus.COMPLETED);

        Double averageScore = testAttemptRepository.findAverageScoreByUserId(userId).orElse(0.0);
        long totalQuestions = userAnswerRepository.countByUserId(userId);
        long correctQuestions = userAnswerRepository.countCorrectByUserId(userId);
        double overallAccuracy = totalQuestions > 0 ? (double) correctQuestions / totalQuestions * 100 : 0;

        List<WeaknessResponse> weaknesses = weaknessService.getUserWeaknesses(userId);
        List<WeaknessResponse> topWeaknesses = weaknesses.stream().limit(5).toList();

        Map<String, Double> subjectAccuracy = weaknesses.stream()
                .collect(Collectors.groupingBy(
                        WeaknessResponse::subjectName,
                        Collectors.averagingDouble(WeaknessResponse::accuracyRate)));

        List<TestAttempt> recentAttempts = testAttemptRepository.findCompletedByUserId(
                userId, PageRequest.of(0, 5));

        List<DashboardResponse.RecentAttemptResponse> recent = recentAttempts.stream()
                .map(a -> new DashboardResponse.RecentAttemptResponse(
                        a.getId(), a.getTest().getTitle(), a.getPercentage(),
                        a.getStatus().name(), a.getStartedAt().toString()))
                .toList();

        return new DashboardResponse(totalTests, averageScore, (int) totalQuestions,
                overallAccuracy, topWeaknesses, subjectAccuracy, recent);
    }
}
