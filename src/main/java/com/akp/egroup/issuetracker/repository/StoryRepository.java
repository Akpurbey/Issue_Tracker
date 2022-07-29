package com.akp.egroup.issuetracker.repository;

import com.akp.egroup.issuetracker.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story,Long> {
    List<Story> findByStatusAndEstimatedPointsLessThanEqualOrderByEstimatedPointsDesc(String estimated, int points);
}
