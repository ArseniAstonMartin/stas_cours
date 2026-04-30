package by.exam.prep.pattern.strategy;

import by.exam.prep.entity.Question;
import by.exam.prep.entity.WeaknessRecord;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdaptiveDifficultyStrategy implements DifficultySelectionStrategy {

    @Override
    public Question.Difficulty selectNextDifficulty(List<WeaknessRecord> weaknesses, Double currentAccuracy) {
        if (currentAccuracy == null || currentAccuracy == 0) {
            return Question.Difficulty.MEDIUM;
        }
        double avgAccuracy = weaknesses.stream()
                .mapToDouble(WeaknessRecord::getAccuracyRate)
                .average()
                .orElse(50.0);

        if (currentAccuracy > 80 && avgAccuracy > 70) {
            return Question.Difficulty.HARD;
        } else if (currentAccuracy < 40 || avgAccuracy < 30) {
            return Question.Difficulty.EASY;
        }
        return Question.Difficulty.MEDIUM;
    }

    @Override
    public String getStrategyName() {
        return "ADAPTIVE";
    }
}
