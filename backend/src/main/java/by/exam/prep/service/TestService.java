package by.exam.prep.service;

import by.exam.prep.dto.request.CreateTestRequest;
import by.exam.prep.dto.response.TestResponse;
import by.exam.prep.entity.Question;
import by.exam.prep.entity.Subject;
import by.exam.prep.entity.Test;
import by.exam.prep.entity.User;
import by.exam.prep.exception.ResourceNotFoundException;
import by.exam.prep.pattern.factory.AdaptiveTestFactory;
import by.exam.prep.pattern.factory.PracticeTestFactory;
import by.exam.prep.repository.QuestionRepository;
import by.exam.prep.repository.SubjectRepository;
import by.exam.prep.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final SubjectRepository subjectRepository;
    private final QuestionRepository questionRepository;
    private final PracticeTestFactory practiceTestFactory;
    private final AdaptiveTestFactory adaptiveTestFactory;

    @Transactional(readOnly = true)
    public Page<TestResponse> getTests(Long subjectId, Pageable pageable) {
        Page<Test> tests;
        if (subjectId != null) {
            tests = testRepository.findBySubjectId(subjectId, pageable);
        } else {
            tests = testRepository.findAll(pageable);
        }
        return tests.map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public TestResponse getTestById(Long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test", "id", id));
        return toResponse(test);
    }

    @Transactional
    public TestResponse createTest(CreateTestRequest request, User creator) {
        Subject subject = subjectRepository.findById(request.subjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject", "id", request.subjectId()));

        List<Question> questions;
        if (request.questionIds() != null && !request.questionIds().isEmpty()) {
            questions = questionRepository.findAllById(request.questionIds());
        } else {
            questions = questionRepository.findBySubjectId(request.subjectId());
        }

        int questionCount = Optional.ofNullable(request.questionCount()).orElse(20);
        boolean isAdaptive = Optional.ofNullable(request.isAdaptive()).orElse(false);

        Test test;
        if (isAdaptive) {
            test = adaptiveTestFactory.createTest(
                    request.title(), request.description(), subject, creator,
                    request.timeLimitMinutes(), questionCount, questions);
        } else {
            test = practiceTestFactory.createTest(
                    request.title(), request.description(), subject, creator,
                    request.timeLimitMinutes(), questionCount, questions);
        }

        if (request.testType() != null) {
            test.setTestType(request.testType());
        }

        test = testRepository.save(test);
        return toResponse(test);
    }

    private TestResponse toResponse(Test test) {
        return new TestResponse(test.getId(), test.getTitle(), test.getDescription(),
                test.getTimeLimitMinutes(), test.getTestType().name(), test.getIsAdaptive(),
                test.getQuestionCount(), test.getSubject().getId(),
                test.getSubject().getName(), test.getCreatedAt());
    }
}
