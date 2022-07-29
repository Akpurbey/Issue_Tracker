package com.akp.egroup.issuetracker.service;

import com.akp.egroup.issuetracker.Exception.ResourceNotFoundException;
import com.akp.egroup.issuetracker.dto.NewIssueRequest;
import com.akp.egroup.issuetracker.model.Developer;
import com.akp.egroup.issuetracker.model.Story;
import com.akp.egroup.issuetracker.model.enums.StoryStatus;
import com.akp.egroup.issuetracker.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryService {
    @Autowired
    private StoryRepository repository;

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private ProjectPlanService projectPlanService;

    public Story createStory(String title, String description,
                             Developer assignedDeveloper, Integer estimate, StoryStatus status) throws ResourceNotFoundException {
        Story story = new Story();
        story.setTitle(title);
        story.setDescription(description);
        story.setAssignedDeveloper(assignedDeveloper);
        story.setEstimatedPoints(estimate);
        story.setStatus(status);
        projectPlanService.recomputeProjectPlan();
        return repository.save(story);
    }

    public Story getStoryById(Long id) throws ResourceNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not Found"));
    }

    public Story updateIssue(Long id, NewIssueRequest request) throws ResourceNotFoundException {
        Story existingIssue = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
        existingIssue.setTitle(request.getTitle());
        existingIssue.setDescription(request.getDescription());
        final Developer developer= developerService.getDeveloper(request.getAssignedDeveloperId());
        existingIssue.setAssignedDeveloper(developer);
        existingIssue.setEstimatedPoints(request.getEstimate());
        existingIssue.setStatus(StoryStatus.valueOf(request.getStatus()));

        projectPlanService.recomputeProjectPlan();

        return repository.save(existingIssue);
    }

    public List<Story> getEstimatedStoriesInDescendingOrderWithEstimationsLowerOrEqual(int points) {
        final String estimated = StoryStatus.ESTIMATED.toString();
        return repository.findByStatusAndEstimatedPointsLessThanEqualOrderByEstimatedPointsDesc(estimated, points);
    }

}
