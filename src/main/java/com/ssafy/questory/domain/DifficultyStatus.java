package com.ssafy.questory.domain;

public enum DifficultyStatus {
    EASY(30),
    MEDIUM(50),
    HARD(100);

    private final int experiencePoints;

    DifficultyStatus(int experiencePoints) {
        this.experiencePoints = experiencePoints;

    }

    public int getExperiencePoints() {
        return experiencePoints;
    }
}
