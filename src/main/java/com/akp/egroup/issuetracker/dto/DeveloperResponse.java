package com.akp.egroup.issuetracker.dto;
import com.akp.egroup.issuetracker.model.Developer;

public class DeveloperResponse extends NewDeveloperRequest {

    private final Long id;

    public DeveloperResponse(Developer developer) {
        this.id = developer.getId();
        this.setName(developer.getName());
    }

    public Long getId() {
        return id;
    }
}
