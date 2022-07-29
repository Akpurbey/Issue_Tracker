package com.akp.egroup.issuetracker.controller;

import com.akp.egroup.issuetracker.dto.IssuesResponse;
import com.akp.egroup.issuetracker.service.IssuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IssuesController {

    @Autowired
    private IssuesService service;

    @GetMapping("/issues")
    public ResponseEntity<IssuesResponse> getAll(){
        return new ResponseEntity<>(new IssuesResponse(service.getAllOrderedByCreationDate()), HttpStatus.OK);
    }


    @GetMapping("/issues/open")
    public ResponseEntity<IssuesResponse> getAllOpen(){
        return new ResponseEntity<>(new IssuesResponse(service.getAllOpenOrderedByCreationDate()),HttpStatus.OK);
    }
}
