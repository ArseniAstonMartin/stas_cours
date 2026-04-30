package by.exam.prep.repository;

import by.exam.prep.entity.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Page<Test> findBySubjectId(Long subjectId, Pageable pageable);
    Page<Test> findByTestType(Test.TestType testType, Pageable pageable);
}
