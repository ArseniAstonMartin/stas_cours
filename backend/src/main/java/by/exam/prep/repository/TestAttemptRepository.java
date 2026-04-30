package by.exam.prep.repository;

import by.exam.prep.entity.TestAttempt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestAttemptRepository extends JpaRepository<TestAttempt, Long> {

    Page<TestAttempt> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT ta FROM TestAttempt ta WHERE ta.user.id = :userId AND ta.status = 'COMPLETED' ORDER BY ta.completedAt DESC")
    List<TestAttempt> findCompletedByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT AVG(ta.percentage) FROM TestAttempt ta WHERE ta.user.id = :userId AND ta.status = 'COMPLETED'")
    Optional<Double> findAverageScoreByUserId(@Param("userId") Long userId);

    long countByUserIdAndStatus(Long userId, TestAttempt.AttemptStatus status);

    @Query("SELECT ta FROM TestAttempt ta LEFT JOIN FETCH ta.userAnswers WHERE ta.id = :id")
    Optional<TestAttempt> findByIdWithAnswers(@Param("id") Long id);
}
