package com.akp.egroup.issuetracker.model;

import org.springframework.util.Assert;

import javax.persistence.*;
import java.beans.Transient;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Entity
public class WeeklyPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer index;

    // only reason to load a weekly plan is to get the stories
    @OneToMany(targetEntity = Story.class, fetch = FetchType.EAGER)
    private Collection<Story> stories =Set.of();
   // private Collection<Stories> stories = Sets.newHashSet();

    protected WeeklyPlan() {
    }

    /**
     * Constructor that supplies the week plan index.
     *
     * @param index - positive value.
     */
    public WeeklyPlan(int index) {
        Assert.isTrue(index > 0, "Index must be 1 based (positive).");
    }

    public Long getId() {
        return id;
    }

    protected Integer getIndex() {
        return index;
    }

    protected void setIndex(Integer index) {
        this.index = index;
    }

    public Collection<Story> getStories() {
        return stories;
    }

    protected void setStories(Collection<Story> stories) {
        this.stories = stories;
    }

    /**
     * Adds a story to the list of stories of this weekly plan.
     *
     * @param story -  Not null.
     */
    public void addStory(Story story) {
        requireNonNull(story);
        this.stories.add(story);
    }

    /**
     * The sum of the estimation points, for all the stories contained by this instance.
     *
     * @return int value.
     */
    @Transient
    public int getTotalPoints() {
        return stories.stream().mapToInt(Story::getEstimatedPoints).
                reduce(0, (left, right) -> left + right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        final List<String> asStrings = stories.stream().map(Story::toString).collect(Collectors.toList());
        return String.join(",", asStrings);
    }
}
