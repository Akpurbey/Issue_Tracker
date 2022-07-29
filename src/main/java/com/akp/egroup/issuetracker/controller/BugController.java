package com.akp.egroup.issuetracker.controller;

import com.akp.egroup.issuetracker.Exception.ResourceNotFoundException;
import com.akp.egroup.issuetracker.controller.helper.AbstractIssuesController;
import com.akp.egroup.issuetracker.dto.IssueResponse;
import com.akp.egroup.issuetracker.dto.NewIssueRequest;
import com.akp.egroup.issuetracker.model.Bugs;
import com.akp.egroup.issuetracker.model.Developer;
import com.akp.egroup.issuetracker.model.enums.BugPriority;
import com.akp.egroup.issuetracker.model.enums.BugStatus;
import com.akp.egroup.issuetracker.service.BugService;
import com.akp.egroup.issuetracker.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bug")
public class BugController extends AbstractIssuesController {
    @Autowired
    private final BugService service;

    @Autowired
    public BugController(DeveloperService developerService, BugService service) {
        super();
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Bugs> get(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<Bugs>(service.getBugById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IssueResponse> update(@PathVariable Long id, @RequestBody NewIssueRequest request) throws ResourceNotFoundException {
        return new ResponseEntity<>(new IssueResponse(service.updateBug(id, request)), HttpStatus.OK);
    }

    @PostMapping("/createBug")
    public ResponseEntity<IssueResponse> create(@RequestBody NewIssueRequest request) throws ResourceNotFoundException {
        Bugs createdBug = createBug(request);
        return new ResponseEntity<IssueResponse>(new IssueResponse(createdBug), HttpStatus.CREATED);
    }

    @Transactional
    private Bugs createBug(NewIssueRequest request) throws ResourceNotFoundException {
        validateTitleAndStatus(request);
        Assert.notNull(request.getPriority(), "Priority is null.");
        final Developer developer = getAssignedDeveloper(request.getAssignedDeveloperId());
        final BugStatus status = BugStatus.valueOf(request.getStatus());
        final BugPriority priority = BugPriority.valueOf(request.getPriority());
        return service.createBug(request.getTitle().trim(), request.getDescription(), developer,
                priority, status);
    }
}
