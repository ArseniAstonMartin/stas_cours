package by.exam.prep.pattern.decorator;

import by.exam.prep.entity.UserAnswer;

import java.util.List;

public interface ScoreCalculator {
    double calculate(List<UserAnswer> answers);
}
