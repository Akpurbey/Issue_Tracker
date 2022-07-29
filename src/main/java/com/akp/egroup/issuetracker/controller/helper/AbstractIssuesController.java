package com.akp.egroup.issuetracker.controller.helper;

import com.akp.egroup.issuetracker.Exception.ResourceNotFoundException;
import com.akp.egroup.issuetracker.dto.NewIssueRequest;
import com.akp.egroup.issuetracker.model.Developer;
import com.akp.egroup.issuetracker.model.Issues;
import com.akp.egroup.issuetracker.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;

public class AbstractIssuesController {
    @Autowired
    private DeveloperService service;

    protected void updateIssue(@RequestBody NewIssueRequest request, Issues issue) throws ResourceNotFoundException {
        validateTitleAndStatus(request);
        issue.setTitle(request.getTitle().trim());
        issue.setDescription(request.getDescription());
        issue.setAssignedDeveloper(getAssignedDeveloper(request.getAssignedDeveloperId()));
    }

   // @Transactional(readOnly = true)
    protected Developer getAssignedDeveloper(Long assignedDeveloperId) throws ResourceNotFoundException {
        Developer developer = null;

        if (assignedDeveloperId != null) {
            developer = service.getDeveloper(assignedDeveloperId);
        }else{
            developer=null;
        }
        return developer;
    }

    protected void validateTitleAndStatus(NewIssueRequest request) throws ResourceNotFoundException {
        Assert.notNull(request, "Issue is null.");
        assert (request.getTitle().isBlank() && request.getTitle().isEmpty()) : "Title is null or empty.";
        Assert.notNull(request.getStatus(), "Issue is null.");

    /*    if(request.getTitle() != null )
        if(request.getTitle().isBlank() && request.getTitle().isEmpty()){
            throw new ResourceNotFoundException("Title is null or empty.");
        }
        checkArgumentNotBlank(request.getTitle(), "Title is null or empty.");
        checkArgumentNonNull(request.getStatus(), "Status is null.");*/
    }

}
