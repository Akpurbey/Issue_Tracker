package com.akp.egroup.issuetracker.service;

import com.akp.egroup.issuetracker.model.WeeklyPlan;
import com.akp.egroup.issuetracker.repository.WeeklyPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
public class WeeklyPlanService {

    @Autowired
    private WeeklyPlanRepository repository;

    public WeeklyPlanService(WeeklyPlanRepository repository) {
        this.repository = repository;
    }

    public List<WeeklyPlan> getAllWeeklyPlans() {
        return repository.findAll();
       // return repository.findAllByOrderByIndexAsc();
    }

    public void clearWeeklyPlans() {
        repository.deleteAll();
    }

    public WeeklyPlan createWeeklyPlan(int index) {
        return repository.save(new WeeklyPlan(index));
    }

    public WeeklyPlan update(WeeklyPlan weeklyPlan) {
        requireNonNull(weeklyPlan);
        return repository.save(weeklyPlan);
    }
}
