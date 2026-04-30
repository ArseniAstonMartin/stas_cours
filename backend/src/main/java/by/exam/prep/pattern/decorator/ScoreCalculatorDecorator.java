package by.exam.prep.pattern.decorator;

import by.exam.prep.entity.UserAnswer;

import java.util.List;

public abstract class ScoreCalculatorDecorator implements ScoreCalculator {

    protected final ScoreCalculator wrapped;

    protected ScoreCalculatorDecorator(ScoreCalculator wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public double calculate(List<UserAnswer> answers) {
        return wrapped.calculate(answers);
    }
}
