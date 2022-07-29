package com.akp.egroup.issuetracker.repository;

import com.akp.egroup.issuetracker.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer,Long> {
}
