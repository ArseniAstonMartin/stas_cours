package by.exam.prep.pattern.observer;

import by.exam.prep.entity.TestAttempt;
import by.exam.prep.entity.UserAnswer;
import by.exam.prep.entity.WeaknessRecord;
import by.exam.prep.repository.WeaknessRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeaknessUpdateListener {

    private final WeaknessRecordRepository weaknessRecordRepository;

    @Async
    @EventListener
    @Transactional
    public void onTestCompletion(TestCompletionEvent event) {
        TestAttempt attempt = event.attempt();
        Long userId = attempt.getUser().getId();

        Map<Long, List<UserAnswer>> answersByTopic = attempt.getUserAnswers().stream()
                .collect(Collectors.groupingBy(ua -> ua.getQuestion().getTopic().getId()));

        answersByTopic.forEach((topicId, answers) -> {
            WeaknessRecord record = weaknessRecordRepository
                    .findByUserIdAndTopicId(userId, topicId)
                    .orElseGet(() -> WeaknessRecord.builder()
                            .user(attempt.getUser())
                            .topic(answers.get(0).getQuestion().getTopic())
                            .build());

            long correct = answers.stream().filter(ua -> Boolean.TRUE.equals(ua.getIsCorrect())).count();
            record.setTotalAttempts(record.getTotalAttempts() + answers.size());
            record.setCorrectAttempts(record.getCorrectAttempts() + (int) correct);
            record.recalculate();

            weaknessRecordRepository.save(record);
            log.debug("Updated weakness for user={}, topic={}: accuracy={}%, level={}",
                    userId, topicId, record.getAccuracyRate(), record.getWeaknessLevel());
        });
    }
}
