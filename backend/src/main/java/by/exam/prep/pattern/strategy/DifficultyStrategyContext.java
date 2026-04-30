package by.exam.prep.pattern.strategy;

import by.exam.prep.entity.Question;
import by.exam.prep.entity.WeaknessRecord;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DifficultyStrategyContext {

    private final Map<String, DifficultySelectionStrategy> strategies;

    public DifficultyStrategyContext(List<DifficultySelectionStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(DifficultySelectionStrategy::getStrategyName, Function.identity()));
    }

    public Question.Difficulty selectDifficulty(String strategyName, List<WeaknessRecord> weaknesses, Double accuracy) {
        DifficultySelectionStrategy strategy = strategies.getOrDefault(strategyName, strategies.get("FIXED"));
        return strategy.selectNextDifficulty(weaknesses, accuracy);
    }
}
