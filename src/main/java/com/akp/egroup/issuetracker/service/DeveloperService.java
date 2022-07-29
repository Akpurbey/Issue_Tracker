package com.akp.egroup.issuetracker.service;

import com.akp.egroup.issuetracker.Exception.ResourceNotFoundException;
import com.akp.egroup.issuetracker.model.Developer;
import com.akp.egroup.issuetracker.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperService {
    @Autowired
    private DeveloperRepository repository;

    public List<Developer> getAllDeveloper(){
        return repository.findAll();
    }

    public Developer getDeveloper(Long id) throws ResourceNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id Not Found"));
    }

    public Developer createDeveloper(Developer developer){
        return repository.save(developer);
    }

    public Developer updateDeveloper(long id, Developer developer) throws ResourceNotFoundException {
        Developer existDeveloper= repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Id Don't matchs"));
        existDeveloper.setName(developer.getName());
        return repository.save(existDeveloper);
  }

  public void removeDeveloper(long id) throws ResourceNotFoundException {
        Developer dev=repository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Resouce Id Not Found"));
        repository.deleteById(id);
  }

  public Long getDeveloperCount(){
        return repository.count();
  }

}
