package by.exam.prep.service;

import by.exam.prep.dto.request.CreateQuestionRequest;
import by.exam.prep.dto.response.QuestionResponse;
import by.exam.prep.entity.Answer;
import by.exam.prep.entity.Question;
import by.exam.prep.entity.Topic;
import by.exam.prep.exception.ResourceNotFoundException;
import by.exam.prep.repository.QuestionRepository;
import by.exam.prep.repository.TopicRepository;
import by.exam.prep.specification.QuestionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TopicRepository topicRepository;

    @Transactional(readOnly = true)
    public Page<QuestionResponse> getQuestions(Long topicId, String difficulty, String type, Pageable pageable) {
        Specification<Question> spec = Specification.where(null);

        if (topicId != null) {
            spec = spec.and(QuestionSpecification.byTopicId(topicId));
        }
        if (difficulty != null) {
            spec = spec.and(QuestionSpecification.byDifficulty(Question.Difficulty.valueOf(difficulty.toUpperCase())));
        }
        if (type != null) {
            spec = spec.and(QuestionSpecification.byType(Question.QuestionType.valueOf(type.toUpperCase())));
        }

        return questionRepository.findAll(spec, pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public QuestionResponse getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", id));
        return toResponse(question);
    }

    @Transactional
    public QuestionResponse createQuestion(CreateQuestionRequest request) {
        Topic topic = topicRepository.findById(request.topicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id", request.topicId()));

        Question question = Question.builder()
                .text(request.text())
                .explanation(request.explanation())
                .questionType(request.questionType())
                .difficulty(Optional.ofNullable(request.difficulty()).orElse(Question.Difficulty.MEDIUM))
                .points(Optional.ofNullable(request.points()).orElse(1))
                .imageUrl(request.imageUrl())
                .topic(topic)
                .build();

        if (request.answers() != null) {
            request.answers().forEach(ar -> {
                Answer answer = Answer.builder()
                        .text(ar.text())
                        .isCorrect(Optional.ofNullable(ar.isCorrect()).orElse(false))
                        .orderIndex(ar.orderIndex())
                        .question(question)
                        .build();
                question.getAnswers().add(answer);
            });
        }

        Question saved = questionRepository.save(question);
        return toResponse(saved);
    }

    private QuestionResponse toResponse(Question q) {
        List<QuestionResponse.AnswerResponse> answers = q.getAnswers().stream()
                .map(a -> new QuestionResponse.AnswerResponse(a.getId(), a.getText(),
                        a.getIsCorrect(), a.getOrderIndex()))
                .toList();

        return new QuestionResponse(q.getId(), q.getText(), q.getExplanation(),
                q.getQuestionType().name(), q.getDifficulty().name(), q.getPoints(),
                q.getImageUrl(), q.getTopic().getId(), q.getTopic().getName(), answers);
    }
}
