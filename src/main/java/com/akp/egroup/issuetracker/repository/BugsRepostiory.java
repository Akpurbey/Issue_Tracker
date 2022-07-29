package com.akp.egroup.issuetracker.repository;

import com.akp.egroup.issuetracker.model.Bugs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugsRepostiory extends JpaRepository<Bugs,Long> {
}
