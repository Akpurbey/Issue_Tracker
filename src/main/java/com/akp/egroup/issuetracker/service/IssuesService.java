package com.akp.egroup.issuetracker.service;

import com.akp.egroup.issuetracker.Exception.ResourceNotFoundException;
import com.akp.egroup.issuetracker.model.Issues;
import com.akp.egroup.issuetracker.repository.IssuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("issuesService")
public class IssuesService {
    @Autowired
    private IssuesRepository repository;

    public List<Issues> getAllIssue(){
        return repository.findAll();
    }

    public void createIssue(Issues issues){
         repository.save(issues);
    }

    public Issues updateIssue(Issues issues, Long id) throws ResourceNotFoundException {
        Issues existingIssue= repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Id Dont matchs"));
        existingIssue.setTitle(issues.getTitle());
        existingIssue.setDescription(issues.getDescription());
        existingIssue.setAssignedDeveloper(issues.getAssignedDeveloper());
        existingIssue.setCreationDate(LocalDate.now());
        return repository.save(existingIssue);
    }

    public void deleteIssue(Long id) throws ResourceNotFoundException {

        Issues issue = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Id Dont matchs"));
        repository.deleteById(id);
    }
    public List<Issues> getAllOrderedByCreationDate() {
        return repository.findAllByOrderByCreationDateAsc();
    }

    public List<Issues> getAllOpenOrderedByCreationDate() {
        return repository.findAllOpenIssuesOrderByCreationDateAsc();
    }

}
