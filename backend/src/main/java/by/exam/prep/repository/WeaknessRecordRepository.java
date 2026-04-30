package by.exam.prep.repository;

import by.exam.prep.entity.WeaknessRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeaknessRecordRepository extends JpaRepository<WeaknessRecord, Long> {

    List<WeaknessRecord> findByUserId(Long userId);

    Optional<WeaknessRecord> findByUserIdAndTopicId(Long userId, Long topicId);

    @Query("SELECT wr FROM WeaknessRecord wr WHERE wr.user.id = :userId ORDER BY wr.accuracyRate ASC")
    List<WeaknessRecord> findWeakestTopics(@Param("userId") Long userId);

    @Query("SELECT wr FROM WeaknessRecord wr WHERE wr.user.id = :userId AND wr.weaknessLevel IN ('WEAK', 'CRITICAL')")
    List<WeaknessRecord> findCriticalWeaknesses(@Param("userId") Long userId);
}
