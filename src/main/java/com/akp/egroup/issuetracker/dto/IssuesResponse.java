package com.akp.egroup.issuetracker.dto;

import com.akp.egroup.issuetracker.model.Issues;

import java.util.List;
import java.util.stream.Collectors;

public class IssuesResponse {

    private List<IssueResponse> issues;

    public IssuesResponse(List<Issues> issues) {
        this.issues = issues.stream()
                .map(IssueResponse::new)
                .collect(Collectors.toList());
    }

    public List<IssueResponse> getIssues() {
        return issues;
    }
}
