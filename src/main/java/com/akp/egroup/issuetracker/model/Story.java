package com.akp.egroup.issuetracker.model;

import com.akp.egroup.issuetracker.model.enums.StoryStatus;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static com.akp.egroup.issuetracker.model.enums.IssueType.Values.STORY;

@Entity
@DiscriminatorValue(STORY)
public class Story extends Issues {

    private Integer estimatedPoints;

    private String status;

    public Story() {
        setStatus(StoryStatus.NEW);
    }
    public StoryStatus getStatus() {
        return StoryStatus.valueOf(status);
    }

    public void setStatus(StoryStatus status) {
        this.status = status.toString();
    }


    public Integer getEstimatedPoints() {
        return estimatedPoints;
    }

    public void setEstimatedPoints(Integer estimatedPoints) {
        this.estimatedPoints = estimatedPoints;
    }

/*
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Stories)) return false;

        if (!super.equals(o)) return false;

        final Stories story = (Stories) o;
        return Objects.equals(estimatedPoints, story.estimatedPoints) &&
                status == story.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), estimatedPoints, status);
    }*/

}
