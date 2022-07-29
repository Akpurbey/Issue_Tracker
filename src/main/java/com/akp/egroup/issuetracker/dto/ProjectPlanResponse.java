package com.akp.egroup.issuetracker.dto;


import com.akp.egroup.issuetracker.model.WeeklyPlan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectPlanResponse {

    private final List<WeeklyPlanResponse> plan;

    public ProjectPlanResponse(List<WeeklyPlan> projectPlan) {
        this.plan = new ArrayList<>();
        this.plan.addAll(projectPlan.stream().map(WeeklyPlanResponse::new).collect(Collectors.toList()));
    }

    public List<WeeklyPlanResponse> getPlan() {
        return plan;
    }
}
