package by.exam.prep.pattern.factory;

import by.exam.prep.entity.Question;
import by.exam.prep.entity.Subject;
import by.exam.prep.entity.Test;
import by.exam.prep.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AdaptiveTestFactory extends TestFactory {

    @Override
    protected Test buildTest(String title, String description, Subject subject, User creator,
                             Integer timeLimitMinutes, Integer questionCount) {
        return Test.builder()
                .title(title)
                .description(description)
                .subject(subject)
                .createdBy(creator)
                .timeLimitMinutes(timeLimitMinutes)
                .questionCount(questionCount)
                .testType(Test.TestType.ADAPTIVE)
                .isAdaptive(true)
                .build();
    }

    @Override
    protected List<Question> selectQuestions(List<Question> available, Integer count) {
        Map<Question.Difficulty, List<Question>> grouped = available.stream()
                .collect(Collectors.groupingBy(Question::getDifficulty));

        List<Question> selected = new ArrayList<>();
        int easyCount = (int) (count * 0.3);
        int mediumCount = (int) (count * 0.4);
        int hardCount = count - easyCount - mediumCount;

        addShuffled(selected, grouped.getOrDefault(Question.Difficulty.EASY, List.of()), easyCount);
        addShuffled(selected, grouped.getOrDefault(Question.Difficulty.MEDIUM, List.of()), mediumCount);
        addShuffled(selected, grouped.getOrDefault(Question.Difficulty.HARD, List.of()), hardCount);

        Collections.shuffle(selected);
        return selected;
    }

    private void addShuffled(List<Question> target, List<Question> source, int count) {
        List<Question> shuffled = new ArrayList<>(source);
        Collections.shuffle(shuffled);
        target.addAll(shuffled.stream().limit(count).toList());
    }
}
