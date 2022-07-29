package com.akp.egroup.issuetracker.model;

import com.akp.egroup.issuetracker.model.enums.BugPriority;
import com.akp.egroup.issuetracker.model.enums.BugStatus;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import static com.akp.egroup.issuetracker.model.enums.IssueType.Values.BUG;

@Entity
@DiscriminatorValue(BUG)
public class Bugs extends Issues {

    @Enumerated(EnumType.STRING)
    private BugPriority priority = BugPriority.CRITICAL;

    private String status;

    public Bugs() {
        setStatus(BugStatus.NEW);
    }

    public BugPriority getPriority() {
        return priority;
    }

    public void setPriority(BugPriority priority) {
        this.priority = priority;
    }

    public BugStatus getStatus() {
        return BugStatus.valueOf(status);
    }

    public void setStatus(BugStatus status) {
        this.status = status.toString();
    }


}
