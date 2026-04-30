package by.exam.prep.service;

import by.exam.prep.dto.request.SubmitAnswerRequest;
import by.exam.prep.dto.response.TestAttemptResponse;
import by.exam.prep.entity.*;
import by.exam.prep.exception.ResourceNotFoundException;
import by.exam.prep.exception.TestAttemptException;
import by.exam.prep.pattern.decorator.BaseScoreCalculator;
import by.exam.prep.pattern.decorator.DifficultyBonusScoreCalculator;
import by.exam.prep.pattern.decorator.ScoreCalculator;
import by.exam.prep.pattern.decorator.TimeBonusScoreCalculator;
import by.exam.prep.pattern.observer.TestEventPublisher;
import by.exam.prep.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestAttemptService {

    private final TestAttemptRepository testAttemptRepository;
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final TestEventPublisher eventPublisher;

    @Transactional
    public TestAttemptResponse startAttempt(Long testId, User user) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Test", "id", testId));

        TestAttempt attempt = TestAttempt.builder()
                .user(user)
                .test(test)
                .status(TestAttempt.AttemptStatus.IN_PROGRESS)
                .build();

        attempt = testAttemptRepository.save(attempt);
        return toResponse(attempt);
    }

    @Transactional
    public TestAttemptResponse submitAnswer(Long attemptId, SubmitAnswerRequest request, User user) {
        TestAttempt attempt = testAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException("TestAttempt", "id", attemptId));

        if (attempt.getStatus() != TestAttempt.AttemptStatus.IN_PROGRESS) {
            throw new TestAttemptException("Test attempt is already completed");
        }

        if (!attempt.getUser().getId().equals(user.getId())) {
            throw new TestAttemptException("You can only submit answers for your own attempts");
        }

        Question question = questionRepository.findById(request.questionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", request.questionId()));

        boolean isCorrect = false;
        Answer selectedAnswer = null;

        if (request.selectedAnswerId() != null) {
            selectedAnswer = answerRepository.findById(request.selectedAnswerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Answer", "id", request.selectedAnswerId()));
            isCorrect = selectedAnswer.getIsCorrect();
        }

        UserAnswer userAnswer = UserAnswer.builder()
                .testAttempt(attempt)
                .question(question)
                .selectedAnswer(selectedAnswer)
                .textAnswer(request.textAnswer())
                .isCorrect(isCorrect)
                .timeSpentSeconds(request.timeSpentSeconds())
                .build();

        attempt.getUserAnswers().add(userAnswer);
        testAttemptRepository.save(attempt);

        return toResponse(attempt);
    }

    @Transactional
    public TestAttemptResponse completeAttempt(Long attemptId, User user) {
        TestAttempt attempt = testAttemptRepository.findByIdWithAnswers(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException("TestAttempt", "id", attemptId));

        if (!attempt.getUser().getId().equals(user.getId())) {
            throw new TestAttemptException("You can only complete your own attempts");
        }

        if (attempt.getStatus() != TestAttempt.AttemptStatus.IN_PROGRESS) {
            throw new TestAttemptException("Test attempt is already completed");
        }

        ScoreCalculator calculator = new BaseScoreCalculator();
        calculator = new DifficultyBonusScoreCalculator(calculator);
        calculator = new TimeBonusScoreCalculator(calculator, 30, 0.1);

        double score = calculator.calculate(attempt.getUserAnswers());
        double maxScore = attempt.getTest().getQuestions().stream()
                .mapToDouble(Question::getPoints)
                .sum();
        if (maxScore == 0) maxScore = attempt.getUserAnswers().size();

        attempt.setScore(score);
        attempt.setMaxScore(maxScore);
        attempt.setPercentage(maxScore > 0 ? (score / maxScore) * 100 : 0);
        attempt.setStatus(TestAttempt.AttemptStatus.COMPLETED);
        attempt.setCompletedAt(LocalDateTime.now());

        attempt = testAttemptRepository.save(attempt);
        eventPublisher.publishTestCompletion(attempt);

        log.info("Test attempt completed: userId={}, attemptId={}, score={}/{}",
                user.getId(), attemptId, score, maxScore);

        return toResponse(attempt);
    }

    @Transactional(readOnly = true)
    public Page<TestAttemptResponse> getUserAttempts(Long userId, Pageable pageable) {
        return testAttemptRepository.findByUserId(userId, pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public TestAttemptResponse getAttemptById(Long id) {
        TestAttempt attempt = testAttemptRepository.findByIdWithAnswers(id)
                .orElseThrow(() -> new ResourceNotFoundException("TestAttempt", "id", id));
        return toResponse(attempt);
    }

    private TestAttemptResponse toResponse(TestAttempt attempt) {
        List<TestAttemptResponse.UserAnswerResponse> answers = attempt.getUserAnswers().stream()
                .map(ua -> new TestAttemptResponse.UserAnswerResponse(
                        ua.getId(), ua.getQuestion().getId(), ua.getQuestion().getText(),
                        ua.getSelectedAnswer() != null ? ua.getSelectedAnswer().getId() : null,
                        ua.getTextAnswer(), ua.getIsCorrect(), ua.getTimeSpentSeconds()))
                .toList();

        return new TestAttemptResponse(attempt.getId(), attempt.getTest().getId(),
                attempt.getTest().getTitle(), attempt.getScore(), attempt.getMaxScore(),
                attempt.getPercentage(), attempt.getTimeSpentSeconds(), attempt.getStatus().name(),
                attempt.getStartedAt(), attempt.getCompletedAt(), answers);
    }
}
