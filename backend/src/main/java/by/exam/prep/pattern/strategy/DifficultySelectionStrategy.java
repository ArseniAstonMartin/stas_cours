package by.exam.prep.pattern.strategy;

import by.exam.prep.entity.Question;
import by.exam.prep.entity.WeaknessRecord;

import java.util.List;

public interface DifficultySelectionStrategy {
    Question.Difficulty selectNextDifficulty(List<WeaknessRecord> weaknesses, Double currentAccuracy);
    String getStrategyName();
}
