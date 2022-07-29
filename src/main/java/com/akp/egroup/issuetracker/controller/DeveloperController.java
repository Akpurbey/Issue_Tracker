package com.akp.egroup.issuetracker.controller;

import com.akp.egroup.issuetracker.Exception.ResourceNotFoundException;
import com.akp.egroup.issuetracker.model.Developer;
import com.akp.egroup.issuetracker.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeveloperController {
    @Autowired
    private DeveloperService developerService;

    @GetMapping("/developer")
    public List<Developer> getDeveloper(){
        return developerService.getAllDeveloper();
    }

    @PostMapping("/developer")
    public ResponseEntity<Developer> createDeveloper(@RequestBody Developer developer){
         developerService.createDeveloper(developer);
         return new ResponseEntity<Developer>(developerService.createDeveloper(developer),HttpStatus.CREATED);
    }

    @GetMapping("/developer/{id}")
    public ResponseEntity<Developer> getDeveloperById(@PathVariable long id) throws ResourceNotFoundException {
        return new ResponseEntity<Developer>(developerService.getDeveloper(id),HttpStatus.OK);
    }

    @PutMapping("/developer/{id}")
    public ResponseEntity<Developer> updateDeveloper(@PathVariable long id,@RequestBody Developer developer) throws ResourceNotFoundException {
         return new ResponseEntity<Developer>(developerService.updateDeveloper(id,developer),HttpStatus.OK);
    }
    @DeleteMapping("/developer/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) throws ResourceNotFoundException {
        developerService.removeDeveloper(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
