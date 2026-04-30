package by.exam.prep.pattern.decorator;

import by.exam.prep.entity.UserAnswer;

import java.util.List;

public class TimeBonusScoreCalculator extends ScoreCalculatorDecorator {

    private final int bonusThresholdSeconds;
    private final double bonusMultiplier;

    public TimeBonusScoreCalculator(ScoreCalculator wrapped, int bonusThresholdSeconds, double bonusMultiplier) {
        super(wrapped);
        this.bonusThresholdSeconds = bonusThresholdSeconds;
        this.bonusMultiplier = bonusMultiplier;
    }

    @Override
    public double calculate(List<UserAnswer> answers) {
        double baseScore = super.calculate(answers);
        double bonus = answers.stream()
                .filter(ua -> Boolean.TRUE.equals(ua.getIsCorrect()))
                .filter(ua -> ua.getTimeSpentSeconds() != null && ua.getTimeSpentSeconds() < bonusThresholdSeconds)
                .mapToDouble(ua -> ua.getQuestion().getPoints() * bonusMultiplier)
                .sum();
        return baseScore + bonus;
    }
}
