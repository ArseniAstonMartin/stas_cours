package by.exam.prep.pattern.decorator;

import by.exam.prep.entity.UserAnswer;

import java.util.List;

public class BaseScoreCalculator implements ScoreCalculator {

    @Override
    public double calculate(List<UserAnswer> answers) {
        return answers.stream()
                .filter(ua -> Boolean.TRUE.equals(ua.getIsCorrect()))
                .mapToDouble(ua -> ua.getQuestion().getPoints())
                .sum();
    }
}
