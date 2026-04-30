package by.exam.prep.repository;

import by.exam.prep.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findBySubjectId(Long subjectId);
    List<Topic> findBySubjectIdAndParentTopicIsNull(Long subjectId);
}
