package by.exam.prep.pattern.factory;

import by.exam.prep.entity.Question;
import by.exam.prep.entity.Subject;
import by.exam.prep.entity.Test;
import by.exam.prep.entity.User;

import java.util.List;

public abstract class TestFactory {

    public Test createTest(String title, String description, Subject subject, User creator,
                           Integer timeLimitMinutes, Integer questionCount, List<Question> questions) {
        Test test = buildTest(title, description, subject, creator, timeLimitMinutes, questionCount);
        test.setQuestions(selectQuestions(questions, questionCount));
        return test;
    }

    protected abstract Test buildTest(String title, String description, Subject subject, User creator,
                                      Integer timeLimitMinutes, Integer questionCount);

    protected abstract List<Question> selectQuestions(List<Question> available, Integer count);
}
