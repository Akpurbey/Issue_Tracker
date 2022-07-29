package com.akp.egroup.issuetracker.dto;

import com.akp.egroup.issuetracker.model.WeeklyPlan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WeeklyPlanResponse {

    private final List<IssueResponse> stories;

    public WeeklyPlanResponse(WeeklyPlan plan) {
        this.stories = new ArrayList<>();
        this.stories.addAll(plan.getStories().stream().map(IssueResponse::new).collect(Collectors.toList()));
    }

    public List<IssueResponse> getStories() {
        return stories;
    }
}
