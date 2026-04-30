package by.exam.prep.pattern.factory;

import by.exam.prep.entity.Question;
import by.exam.prep.entity.Subject;
import by.exam.prep.entity.Test;
import by.exam.prep.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class PracticeTestFactory extends TestFactory {

    @Override
    protected Test buildTest(String title, String description, Subject subject, User creator,
                             Integer timeLimitMinutes, Integer questionCount) {
        return Test.builder()
                .title(title)
                .description(description)
                .subject(subject)
                .createdBy(creator)
                .timeLimitMinutes(timeLimitMinutes)
                .questionCount(questionCount)
                .testType(Test.TestType.PRACTICE)
                .isAdaptive(false)
                .build();
    }

    @Override
    protected List<Question> selectQuestions(List<Question> available, Integer count) {
        Collections.shuffle(available);
        return available.stream().limit(count).toList();
    }
}
