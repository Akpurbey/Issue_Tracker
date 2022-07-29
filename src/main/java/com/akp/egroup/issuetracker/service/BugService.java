package com.akp.egroup.issuetracker.service;

import com.akp.egroup.issuetracker.Exception.ResourceNotFoundException;
import com.akp.egroup.issuetracker.dto.NewIssueRequest;
import com.akp.egroup.issuetracker.model.Bugs;
import com.akp.egroup.issuetracker.model.Developer;
import com.akp.egroup.issuetracker.model.enums.BugPriority;
import com.akp.egroup.issuetracker.model.enums.BugStatus;
import com.akp.egroup.issuetracker.repository.BugsRepostiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class BugService {
    @Autowired
    private BugsRepostiory bugsRepostiory;

    @Autowired
    private DeveloperService service;

    public Bugs createBug(String title, String description, Developer assignedDeveloper, BugPriority priority, BugStatus status) {

        Assert.notNull(title, "Title can not be null!");
        Assert.notNull(status, "Status can not be null!");
        Assert.notNull(priority, "Priority can not be null!");

        Bugs bug = new Bugs();
        bug.setTitle(title);
        bug.setDescription(description);
        bug.setAssignedDeveloper(assignedDeveloper);
        bug.setPriority(priority);
        bug.setStatus(status);
        return bugsRepostiory.save(bug);
    }

    public Bugs getBugById(Long id) throws ResourceNotFoundException {
        return bugsRepostiory.findById(id).orElseThrow(() -> new ResourceNotFoundException("BugId can not be null"));
    }

    public Bugs updateBug(long id, NewIssueRequest bug) throws ResourceNotFoundException {
        Bugs existingBug = bugsRepostiory.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("BugId not found"));
        existingBug.setTitle(bug.getTitle());
        existingBug.setDescription(bug.getDescription());
        final Developer developer = service.getDeveloper(bug.getAssignedDeveloperId());
        existingBug.setAssignedDeveloper(developer);
        existingBug.setPriority(BugPriority.valueOf(bug.getPriority()));
        existingBug.setStatus(BugStatus.valueOf(bug.getStatus()));
        return bugsRepostiory.save(existingBug);
    }
}
