package com.akp.egroup.issuetracker.controller;

import com.akp.egroup.issuetracker.dto.ProjectPlanResponse;
import com.akp.egroup.issuetracker.model.WeeklyPlan;
import com.akp.egroup.issuetracker.service.ProjectPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlanController {

    @Autowired
    private ProjectPlanService service;

    @GetMapping("/plan")
    public ProjectPlanResponse get() {
        final List<WeeklyPlan> projectPlan = service.getProjectPlan();
        return new ProjectPlanResponse(projectPlan);
    }

}
