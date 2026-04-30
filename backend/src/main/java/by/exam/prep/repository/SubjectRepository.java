package by.exam.prep.repository;

import by.exam.prep.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByName(String name);

    @Query("SELECT s FROM Subject s LEFT JOIN FETCH s.topics WHERE s.id = :id")
    Optional<Subject> findByIdWithTopics(Long id);

    @Query("SELECT DISTINCT s FROM Subject s LEFT JOIN FETCH s.topics")
    List<Subject> findAllWithTopics();
}
