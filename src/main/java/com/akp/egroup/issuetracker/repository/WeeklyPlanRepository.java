package com.akp.egroup.issuetracker.repository;

import com.akp.egroup.issuetracker.model.WeeklyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyPlanRepository extends JpaRepository<WeeklyPlan,Long> {
}
