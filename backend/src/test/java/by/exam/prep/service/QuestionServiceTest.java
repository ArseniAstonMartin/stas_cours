package by.exam.prep.service;

import by.exam.prep.dto.request.CreateQuestionRequest;
import by.exam.prep.dto.response.QuestionResponse;
import by.exam.prep.entity.Answer;
import by.exam.prep.entity.Question;
import by.exam.prep.entity.Subject;
import by.exam.prep.entity.Topic;
import by.exam.prep.exception.ResourceNotFoundException;
import by.exam.prep.repository.QuestionRepository;
import by.exam.prep.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private QuestionService questionService;

    private Topic testTopic;
    private Question testQuestion;

    @BeforeEach
    void setUp() {
        Subject subject = Subject.builder().id(1L).name("Math").build();
        testTopic = Topic.builder().id(1L).name("Algebra").subject(subject).build();

        testQuestion = Question.builder()
                .id(1L)
                .text("What is 2+2?")
                .explanation("Basic arithmetic")
                .questionType(Question.QuestionType.SINGLE_CHOICE)
                .difficulty(Question.Difficulty.EASY)
                .points(1)
                .topic(testTopic)
                .answers(List.of(
                        Answer.builder().id(1L).text("3").isCorrect(false).build(),
                        Answer.builder().id(2L).text("4").isCorrect(true).build()
                ))
                .build();
    }

    @Test
    @DisplayName("Should get question by id")
    void getQuestionById_Success() {
        when(questionRepository.findById(1L)).thenReturn(Optional.of(testQuestion));

        QuestionResponse response = questionService.getQuestionById(1L);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.text()).isEqualTo("What is 2+2?");
        assertThat(response.answers()).hasSize(2);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when question not found")
    void getQuestionById_NotFound() {
        when(questionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> questionService.getQuestionById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Question");
    }

    @Test
    @DisplayName("Should create question successfully")
    void createQuestion_Success() {
        CreateQuestionRequest request = new CreateQuestionRequest(
                "New question?", "Explanation", Question.QuestionType.SINGLE_CHOICE,
                Question.Difficulty.MEDIUM, 2, 1L, null,
                List.of(
                        new CreateQuestionRequest.AnswerRequest("Yes", true, 1),
                        new CreateQuestionRequest.AnswerRequest("No", false, 2)
                ));

        when(topicRepository.findById(1L)).thenReturn(Optional.of(testTopic));
        when(questionRepository.save(any(Question.class))).thenReturn(testQuestion);

        QuestionResponse response = questionService.createQuestion(request);

        assertThat(response).isNotNull();
        assertThat(response.topicId()).isEqualTo(1L);
    }
}
