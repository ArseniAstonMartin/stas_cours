package by.exam.prep.pattern.strategy;

import by.exam.prep.entity.Question;
import by.exam.prep.entity.WeaknessRecord;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FixedDifficultyStrategy implements DifficultySelectionStrategy {

    @Override
    public Question.Difficulty selectNextDifficulty(List<WeaknessRecord> weaknesses, Double currentAccuracy) {
        return Question.Difficulty.MEDIUM;
    }

    @Override
    public String getStrategyName() {
        return "FIXED";
    }
}
