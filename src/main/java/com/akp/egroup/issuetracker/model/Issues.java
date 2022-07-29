package com.akp.egroup.issuetracker.model;

import com.akp.egroup.issuetracker.model.enums.BugStatus;
import com.akp.egroup.issuetracker.model.enums.IssueType;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "issueType", discriminatorType = DiscriminatorType.STRING, length = 10)
public class Issues {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long issueId;

    @Column(nullable = false)
    private String title;

    private String description;

/*    @Column(nullable = false)
    private String status;*/

    @CreatedDate
    @Column(nullable = false)
    private LocalDate creationDate = LocalDate.now();

    @ManyToOne(targetEntity = Developer.class)
    private Developer assignedDeveloper;

    public Issues() {
    }

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Developer getAssignedDeveloper() {
        return assignedDeveloper;
    }

    public void setAssignedDeveloper(Developer assignedDeveloper) {
        this.assignedDeveloper = assignedDeveloper;
    }

    @Transient
    public final IssueType getIssueType() {
        DiscriminatorValue val = this.getClass().getAnnotation(DiscriminatorValue.class);

        return val == null ? null : IssueType.valueOf(val.value());
    }
}
