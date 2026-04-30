package by.exam.prep.repository;

import by.exam.prep.entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {

    List<UserAnswer> findByTestAttemptId(Long testAttemptId);

    @Query("SELECT ua FROM UserAnswer ua WHERE ua.testAttempt.user.id = :userId AND ua.question.topic.id = :topicId")
    List<UserAnswer> findByUserIdAndTopicId(@Param("userId") Long userId, @Param("topicId") Long topicId);

    @Query("SELECT COUNT(ua) FROM UserAnswer ua WHERE ua.testAttempt.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(ua) FROM UserAnswer ua WHERE ua.testAttempt.user.id = :userId AND ua.isCorrect = true")
    long countCorrectByUserId(@Param("userId") Long userId);
}
