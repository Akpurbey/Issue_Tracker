package com.akp.egroup.issuetracker.repository;

import com.akp.egroup.issuetracker.model.Issues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssuesRepository extends JpaRepository<Issues,Long> {
    List<Issues> findAllByOrderByCreationDateAsc();

    @Query("select i from Issues i " +
            "where i.status not in ('RESOLVED', 'COMPLETED') " +
            "order by i.creationDate")
    List<Issues> findAllOpenIssuesOrderByCreationDateAsc();
}
