package com.akp.egroup.issuetracker.model.enums;

public enum IssueType {
    BUG, STORY;
    public final static class Values {
        public static final String BUG = "BUG";
        public static final String STORY = "STORY";
        private Values() {
        }
    }
}
