package com.akp.egroup.issuetracker.dto;

import com.akp.egroup.issuetracker.model.Bugs;
import com.akp.egroup.issuetracker.model.Issues;
import com.akp.egroup.issuetracker.model.Story;
import com.akp.egroup.issuetracker.model.enums.IssueType;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IssueResponse extends NewIssueRequest {

    private Long id;
    private DeveloperResponse assignedDeveloper;

    public IssueResponse() {
    }

    public IssueResponse(Issues issue) {

        this.creationDate = issue.getCreationDate();
        this.issueType = issue.getIssueType().toString();

        if (Objects.nonNull(issue.getAssignedDeveloper())) {
            this.assignedDeveloper = new DeveloperResponse(issue.getAssignedDeveloper());
        }

        this.description = issue.getDescription();
        this.title = issue.getTitle();

        this.id = issue.getIssueId();

        if (Objects.nonNull(issue.getIssueType())) {
            populateConcreteFields(issue, issue.getIssueType());
        }
    }

    private void populateConcreteFields(Issues issue, IssueType issueType) {
        switch (issueType) {
            case BUG:
                populateBugFields((Bugs) issue);
                break;
            case STORY:
                populateStoryFields((Story) issue);
                break;
        }
    }

    private void populateStoryFields(Story issue) {
        this.estimate = issue.getEstimatedPoints();
        this.status = issue.getStatus().toString();
    }

    private void populateBugFields(Bugs issue) {
        this.priority = issue.getPriority().toString();
        this.status = issue.getStatus().toString();
    }

    public Long getId() {
        return id;
    }

    public DeveloperResponse getAssignedDeveloper() {
        return assignedDeveloper;
    }
}
