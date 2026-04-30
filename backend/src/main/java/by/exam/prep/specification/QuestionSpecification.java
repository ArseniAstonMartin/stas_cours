package by.exam.prep.specification;

import by.exam.prep.entity.Question;
import org.springframework.data.jpa.domain.Specification;

public final class QuestionSpecification {

    private QuestionSpecification() {}

    public static Specification<Question> byTopicId(Long topicId) {
        return (root, query, cb) -> cb.equal(root.get("topic").get("id"), topicId);
    }

    public static Specification<Question> byDifficulty(Question.Difficulty difficulty) {
        return (root, query, cb) -> cb.equal(root.get("difficulty"), difficulty);
    }

    public static Specification<Question> byType(Question.QuestionType type) {
        return (root, query, cb) -> cb.equal(root.get("questionType"), type);
    }

    public static Specification<Question> bySubjectId(Long subjectId) {
        return (root, query, cb) -> cb.equal(root.get("topic").get("subject").get("id"), subjectId);
    }
}
