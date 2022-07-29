package com.akp.egroup.issuetracker.service;

import com.akp.egroup.issuetracker.Exception.ResourceNotFoundException;
import com.akp.egroup.issuetracker.model.Story;
import com.akp.egroup.issuetracker.model.WeeklyPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectPlanService {

    private static final int DEVELOPER_POINTS_PER_WEEK = 10;

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private StoryService storyService;

    @Autowired
    private WeeklyPlanService weeklyPlanService;

    public List<WeeklyPlan> getProjectPlan() {
        return weeklyPlanService.getAllWeeklyPlans();
    }


    public void recomputeProjectPlan() throws ResourceNotFoundException {
        weeklyPlanService.clearWeeklyPlans();

        final long developerCount = developerService.getDeveloperCount();
        if (developerCount == 0L) {
            return;
        }

        final int maxPoints = (int) (developerCount * DEVELOPER_POINTS_PER_WEEK);
        final List<Story> estimatedStories = storyService.getEstimatedStoriesInDescendingOrderWithEstimationsLowerOrEqual
                (maxPoints);

        if (estimatedStories.isEmpty()) {
            return;
        }
        List<Story> stories = estimatedStories.stream().collect(Collectors.toList());
        checkStoriesInAscOrder(stories);
        createDistributionPlan(stories, maxPoints);
    }

    // standard greedy algorithm ~= O(n*log(n)) but object removal from array list might slow it down
    private void createDistributionPlan(final List<Story> stories, final int maxPoints) throws ResourceNotFoundException {

        int initialStoryCount = stories.size();
        int weekIndex = 1;
        WeeklyPlan currentPlan = weeklyPlanService.createWeeklyPlan(weekIndex);
        int currentPoints = 0;

        List<Story> leftOverStories = stories;

        while (!leftOverStories.isEmpty() && initialStoryCount >= weekIndex) {

            final int availablePoints = maxPoints - currentPoints;

            if (canStillSelectStories(leftOverStories, availablePoints)) {

                final boolean isCurrentPlanEmpty = currentPlan.getStories().isEmpty();

                final int storyIndex = getLargestStoryThatFits(leftOverStories, isCurrentPlanEmpty, availablePoints);
                final Story currentStory = leftOverStories.get(storyIndex);

                leftOverStories = updateStoriesList(leftOverStories, storyIndex);

                currentPoints += currentStory.getEstimatedPoints();
                currentPlan.addStory(currentStory);

            } else {

                weeklyPlanService.update(currentPlan);
                // create the next plan
                currentPoints = 0;
                weekIndex++;
                currentPlan = weeklyPlanService.createWeeklyPlan(weekIndex);
            }
        }

        // save the last plan created
        if (currentPoints != 0) {
            weeklyPlanService.update(currentPlan);
        }
    }

    private boolean canStillSelectStories(List<Story> stories, int availablePoints) {
        return availablePoints >= stories.get(stories.size() - 1).getEstimatedPoints();
    }

    private List<Story> updateStoriesList(List<Story> stories, int storyIndex) {

        if (storyIndex == 0) {
            stories = stories.subList(1, stories.size());
        } else {
            stories.remove(storyIndex);
        }
        return stories;
    }

    private int getLargestStoryThatFits(List<Story> stories, final boolean isCurrentPlanEmpty, int
            availablePoints) throws ResourceNotFoundException {
        if(stories.isEmpty()){
            throw new ResourceNotFoundException("Stories is empty");
        }

        if (isCurrentPlanEmpty) {
            return 0;
        } else {
            final Integer indexOfTheLargestStoryThatFits = binarySearch(stories, availablePoints);
            return indexOfTheLargestStoryThatFits;
        }

    }

    private void checkStoriesInAscOrder(List<Story> stories) {
        for (int current = 1, last = 0; current < stories.size(); last = current++) {

            final Story currentStory = stories.get(current);
            final Story previousStory = stories.get(last);
            assert(currentStory.getEstimatedPoints() <= previousStory.getEstimatedPoints()):
                    "Returned stories not in descending order";
        }
    }

    // copied from the Collections.binarySearch and customized
    private static Integer binarySearch(List<? extends Story> list, Integer estimatedPoints) {

        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            Integer midVal = list.get(mid).getEstimatedPoints();
            int cmp = Integer.compare(estimatedPoints, midVal);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return low;  // key not found
    }
}
