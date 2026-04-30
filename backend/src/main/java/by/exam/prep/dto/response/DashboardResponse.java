package by.exam.prep.dto.response;

import java.util.List;
import java.util.Map;

public record DashboardResponse(
        Integer totalTestsTaken,
        Double averageScore,
        Integer totalQuestionsAnswered,
        Double overallAccuracy,
        List<WeaknessResponse> topWeaknesses,
        Map<String, Double> subjectAccuracy,
        List<RecentAttemptResponse> recentAttempts
) {
    public record RecentAttemptResponse(
            Long attemptId,
            String testTitle,
            Double percentage,
            String status,
            String startedAt
    ) {}
}
