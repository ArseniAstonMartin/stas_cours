package by.exam.prep.repository;

import by.exam.prep.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {

    Page<Question> findByTopicId(Long topicId, Pageable pageable);

    @Query("SELECT q FROM Question q WHERE q.topic.subject.id = :subjectId")
    List<Question> findBySubjectId(@Param("subjectId") Long subjectId);

    @Query("SELECT q FROM Question q WHERE q.topic.id IN :topicIds AND q.difficulty = :difficulty")
    List<Question> findByTopicIdsAndDifficulty(
            @Param("topicIds") List<Long> topicIds,
            @Param("difficulty") Question.Difficulty difficulty
    );

    @Query("SELECT q FROM Question q WHERE q.topic.id IN :topicIds ORDER BY FUNCTION('RANDOM')")
    List<Question> findRandomByTopicIds(@Param("topicIds") List<Long> topicIds, Pageable pageable);

    long countByTopicId(Long topicId);
}
