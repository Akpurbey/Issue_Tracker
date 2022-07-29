package com.akp.egroup.issuetracker.controller;


import com.akp.egroup.issuetracker.Exception.ResourceNotFoundException;
import com.akp.egroup.issuetracker.controller.helper.AbstractIssuesController;
import com.akp.egroup.issuetracker.dto.IssueResponse;
import com.akp.egroup.issuetracker.dto.NewIssueRequest;
import com.akp.egroup.issuetracker.model.Developer;
import com.akp.egroup.issuetracker.model.Story;
import com.akp.egroup.issuetracker.model.enums.StoryStatus;
import com.akp.egroup.issuetracker.service.DeveloperService;
import com.akp.egroup.issuetracker.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

import static com.akp.egroup.issuetracker.model.enums.StoryStatus.NEW;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


@RestController
public class StoryController extends AbstractIssuesController {

    @Autowired
    private final StoryService service;

    @Autowired
    public StoryController(DeveloperService developerService, StoryService service) {
        super();
        this.service = service;
    }

    @GetMapping("/story/{id}")
    public ResponseEntity<IssueResponse> get(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(new IssueResponse(service.getStoryById(id)), HttpStatus.OK);
    }

    @PutMapping("/story/{id}")
    public ResponseEntity<IssueResponse> update(@PathVariable Long id, @RequestBody NewIssueRequest request) throws ResourceNotFoundException {
        return new ResponseEntity<>(new IssueResponse(service.updateIssue(id, request)), HttpStatus.OK);
    }

    @PostMapping("/story/createStrory")
    public ResponseEntity<IssueResponse> create(@RequestBody NewIssueRequest request) throws ResourceNotFoundException {
        Story createdStory = createStory(request);
        return new ResponseEntity<>(new IssueResponse(createdStory), HttpStatus.CREATED);
    }

    @Transactional
    private Story createStory(NewIssueRequest request) throws ResourceNotFoundException {
        validateTitleAndStatus(request);
        validateEstimate(request);
        final StoryStatus status = StoryStatus.valueOf(request.getStatus());
        assert (status == NEW || nonNull(request.getEstimate())) : "Stories that are ESTIMATED or CLOSED must " +
                "have their estimate set";

        final Developer developer = getAssignedDeveloper(request.getAssignedDeveloperId());
        return service.createStory(request.getTitle().trim(), request.getDescription(),
                developer, request.getEstimate(), status);
    }

    private void validateEstimate(NewIssueRequest request) {
        assert isNull(request.getEstimate()) || request.getEstimate() >= 0 : "Negative estimate.";
    }
}
