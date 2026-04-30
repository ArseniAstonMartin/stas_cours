package by.exam.prep.pattern.decorator;

import by.exam.prep.entity.Question;
import by.exam.prep.entity.UserAnswer;

import java.util.List;

public class DifficultyBonusScoreCalculator extends ScoreCalculatorDecorator {

    public DifficultyBonusScoreCalculator(ScoreCalculator wrapped) {
        super(wrapped);
    }

    @Override
    public double calculate(List<UserAnswer> answers) {
        double baseScore = super.calculate(answers);
        double difficultyBonus = answers.stream()
                .filter(ua -> Boolean.TRUE.equals(ua.getIsCorrect()))
                .mapToDouble(ua -> {
                    Question.Difficulty diff = ua.getQuestion().getDifficulty();
                    return switch (diff) {
                        case HARD -> ua.getQuestion().getPoints() * 0.5;
                        case MEDIUM -> ua.getQuestion().getPoints() * 0.2;
                        case EASY -> 0;
                    };
                })
                .sum();
        return baseScore + difficultyBonus;
    }
}
